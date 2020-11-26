package com.swedbank.samples.merchant;

public class RedirectHtmlString {
    
    private String htmlString;
    
    public RedirectHtmlString() {
        htmlString = new String("<html>\n" +
                "<head>\n" +
                "<title>Swedbank Pay Payment</title>\n" +
                "<link rel=\"stylesheet\" href=\"https://design.swedbankpay.com/v/4.3.0/styles/dg-style.css%22 >\n" +
                "<meta name=\"viewport\" content=\"width=device-width\">\n" +
                "<meta http-equiv=\"refresh\" content=\"0;url=INTENT_URL\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"text-center\">\n" +
                "<img src=\"https://design.swedbankpay.com/v/4.3.0/img/swedbankpay-logo.svg%22  alt=\"Swedbank Pay\" height=\"120\">\n" +
                "<p><a class=\"btn btn-executive\" href=\"INTENT_URL\">Back to app</a></p>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
    }

    public String getHtmlString(String url) {
        return htmlString.replaceAll("INTENT_URL", url);
    }
}
