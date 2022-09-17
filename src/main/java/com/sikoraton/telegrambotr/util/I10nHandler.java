package com.sikoraton.telegrambotr.util;

import java.util.Map;

public class I10nHandler {
    public static final String DEFAULT_LANGUAGE = "ua";

    public static String getTextByLanguage(Map<String, String> text, String languageCod){
        if (text.containsKey(languageCod))
            return text.get(languageCod);
        return text.get(DEFAULT_LANGUAGE);
    }
}
