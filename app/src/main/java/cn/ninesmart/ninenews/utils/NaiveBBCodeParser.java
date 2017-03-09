package cn.ninesmart.ninenews.utils;

/**
 * Author   : perqin
 * Date     : 17-3-9
 */

public final class NaiveBBCodeParser {
    private NaiveBBCodeParser() {
        // Prevent construction
    }

    public static String toHtml(String bbCode) {
        return bbCode
                .replaceAll("\\[p](.+?)\\[/p]", "<p>$1</p>")
                .replaceAll("\\[img](.+?)\\[/img]", "<img src=\"$1\" />")
                .replaceAll("\\[img=(.+?)](.+?)\\[/img]", "<img src=\"$2\" /><p><center><i>▲ $1</i></center></p>");
    }
}
