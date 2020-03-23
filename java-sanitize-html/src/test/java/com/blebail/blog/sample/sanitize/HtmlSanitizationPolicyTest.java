package com.blebail.blog.sample.sanitize;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

public final class HtmlSanitizationPolicyTest {

    @Test
    public void shouldNotAllowAnyHtmlElementOnStrictMode() {
        String text = "Text with <a href=\"https://example.com\">a link</a> " +
                "and<script>alert('javascript');</script>";

        String sanitized = HtmlSanitizationPolicy.STRICT.sanitize(text);

        assertThat(sanitized).isEqualTo("Text with a link and");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "A <h1>Title</h1> and a <p>paragraph</p>",
            "<strong>Strong</strong> and <em>emphasized</em>",
            "Code with <span style=\"color:red\">style</span>",
            "An <img src=\"https://example.com/img.jpg\" width=\"200\" />",
            "A <a href=\"https://example.com\" rel=\"nofollow\">link</a>"
    })
    public void shouldAllowCommonArticleElements(String text) {
        String sanitized = HtmlSanitizationPolicy.ARTICLE.sanitize(text);

        assertThat(sanitized).isEqualTo(text);
    }

    @Test
    public void shouldAllowMyElementOnCustomMode() {
        String text = "Text with <my-element>Mine</my-element>";

        String sanitized = HtmlSanitizationPolicy.CUSTOM.sanitize(text);

        assertThat(sanitized).isEqualTo(text);
    }
}