package com.ssw.restohub.service.impl;

import com.lowagie.text.DocumentException;
import com.ssw.restohub.service.HtmlToPdfService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import static org.thymeleaf.templatemode.TemplateMode.HTML;

@Service
public class HtmlToPdfServiceImpl implements HtmlToPdfService {
    @Override
    public ByteArrayInputStream convertHtmlToPdf(Object data, String htmlFileName) throws DocumentException {
        String resolvedHtml = DataIntoHtml(data,htmlFileName);
        Document xhtmlDocument = XHTMLDocument(resolvedHtml);
        return PdfInputStream(xhtmlDocument);
    }

    private String DataIntoHtml(Object data, String htmlFileName){
        ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        Context context = new Context();
        TemplateEngine templateEngine = new TemplateEngine();
        classLoaderTemplateResolver.setPrefix("/templates/");
        classLoaderTemplateResolver.setSuffix(".html");
        classLoaderTemplateResolver.setTemplateMode(HTML);
        classLoaderTemplateResolver.setCharacterEncoding("UTF-8");
        templateEngine.setTemplateResolver(classLoaderTemplateResolver);
        context.setVariable("data",data);
        return templateEngine.process(htmlFileName,context);
    }

    private Document XHTMLDocument(String resolvedHTML){
        Document document = Jsoup.parse(resolvedHTML,"UTF-8");
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document;
    }

    private ByteArrayInputStream PdfInputStream(Document xhtmlDocument) throws DocumentException {
        ITextRenderer renderer = new ITextRenderer();
        SharedContext sharedContext = renderer.getSharedContext();
        sharedContext.setPrint(true);
        sharedContext.setInteractive(false);
        renderer.setDocumentFromString(xhtmlDocument.html());
        renderer.layout();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        renderer.createPDF(byteArrayOutputStream,false);
        renderer.finishPDF();
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

}
