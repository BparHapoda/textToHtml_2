package org.example;

public enum Tag {
    DOC_START("<html><body>"),   //  обозначение html файла
    DOC_END("</body></html>"),// конец файла
    PARAGRAPH_START("<p>"),         //  абзац
    PARAGRAPH_END("</p>"),
    UL_START("<ul>"), //  ненумерованный список
    UL_END("</ul>"),
    OL_START("<ol>"),        // нумерованный список
    OL_END("</ol>"),
    LI_START("<li>"),
    LI_END("</li>");    // пункт списка внутри списка

    String name;

    Tag(String name) {
        this.name = name;
    }

}
