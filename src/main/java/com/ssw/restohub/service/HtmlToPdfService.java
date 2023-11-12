package com.ssw.restohub.service;

import com.lowagie.text.DocumentException;

import java.io.ByteArrayInputStream;

public interface HtmlToPdfService {

    ByteArrayInputStream convertHtmlToPdf(Object data, String htmlFileName) throws DocumentException;
}
