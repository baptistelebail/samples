package com.blebail.blog.sample.sanitize;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public enum HtmlSanitizationPolicy implements SanitizationPolicy {

    STRICT(new HtmlPolicyBuilder()
            .toFactory()),

    ARTICLE(Sanitizers.BLOCKS
            .and(Sanitizers.FORMATTING)
            .and(Sanitizers.STYLES)
            .and(Sanitizers.IMAGES)
            .and(Sanitizers.LINKS)),

    CUSTOM(new HtmlPolicyBuilder()
            .allowElements("my-element")
            .toFactory());

    private final PolicyFactory policyFactory;

    HtmlSanitizationPolicy(PolicyFactory policyFactory) {
        this.policyFactory = policyFactory;
    }

    @Override
    public String sanitize(String input) {
        return policyFactory.sanitize(input);
    }
}
