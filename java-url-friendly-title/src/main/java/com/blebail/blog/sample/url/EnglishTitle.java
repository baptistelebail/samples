package com.blebail.blog.sample.url;

import com.google.common.base.Strings;
import com.google.common.net.UrlEscapers;

import java.util.Objects;

public class EnglishTitle implements SeoString {

    private final String title;

    public EnglishTitle(String title) {
        this.title = Objects.requireNonNull(title);
    }

    /**
     * Builds an url friendly string out of an original string, by :
     * - removing all non alphanumerical characters except '&'
     * - removing trailing spaces
     * - replacing whitespaces with a dash
     * - replacing & with 'and'
     */
    @Override
    public String toUrlFriendlyString() {
        String computedValue = "";

        if (!Strings.isNullOrEmpty(title)) {
            computedValue = title.toLowerCase()
                    .replaceAll("[^a-zA-Z0-9 &]", "")
                    .trim()
                    .replace(' ', '-')
                    .replace("&", "and");
        }

        return UrlEscapers.urlPathSegmentEscaper().escape(computedValue);
    }
}
