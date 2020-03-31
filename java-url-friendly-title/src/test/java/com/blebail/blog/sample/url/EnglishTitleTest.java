package com.blebail.blog.sample.url;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EnglishTitleTest {

    @Test
    public void shouldPutEveryCharactersToLowerCase() {
        assertThat(new EnglishTitle("ASimpleTitle").toUrlFriendlyString())
                .isEqualTo("asimpletitle");
    }

    @Test
    public void shouldDeleteSpecialCharacters() {
        assertThat(new EnglishTitle("a!si?m=p'le#tit%l)e+").toUrlFriendlyString())
                .isEqualTo("asimpletitle");
    }

    @Test
    public void shouldReplaceSpaceWithDash() {
        assertThat(new EnglishTitle("a simple title").toUrlFriendlyString())
                .isEqualTo("a-simple-title");
    }

    @Test
    public void shouldReplaceAmpersandWithAnd() {
        assertThat(new EnglishTitle("The title of a nice & simple post !").toUrlFriendlyString())
                .isEqualTo("the-title-of-a-nice-and-simple-post");
    }
}