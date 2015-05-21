/**
 * @author Prasanna Kumar Ramachandran
 * @since 05/21/2015
 *
 */

package com.xml.topdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class XmlToPdf {
	//1st Argument - Fully qualified Path of XML file (with filename and extension)
	//2nd Argument - Fully qualified Path of PDF file (with filename and extension)
	//Example: java -jar target/adobe-xml-pdf-1.0.0.jar C:/Users/prasannakumarr/Desktop/xmlFile/Input.xml C:/Users/prasannakumarr/Desktop/pdfFile/Output.pdf
	public static void main(String[] args) throws Exception {
		
		if(args.length == 2) {
			System.out.println("XML to PDF conversion started");
			String xmlFullPath = args[0];
			String pdfFullPath = args[1];
			String pdfPath = pdfFullPath.substring(0 ,pdfFullPath.lastIndexOf("/"));
			File pdfPathDirectory = new File(pdfPath);
			TransformerFactory tFactory = null;
			Transformer transformer = null;
			Document document = new Document();
			PdfWriter writer = null;
			StreamSource streamSource = null;
			StreamResult streamResult = null;
			FileOutputStream pdfOutputStream = null;
			try {
				tFactory = TransformerFactory.newInstance();
				transformer = tFactory.newTransformer();
				streamSource = new StreamSource(xmlFullPath);
				if(!(pdfPathDirectory.exists() && pdfPathDirectory.isDirectory())) {
					System.out.println(pdfPath + " directory does not exist. Creating the directory");
					pdfPathDirectory.mkdir();
				}
				streamResult = new StreamResult(new FileOutputStream(pdfPath + "/temp.html"));
				pdfOutputStream = new FileOutputStream(pdfFullPath);
				transformer.transform(streamSource, streamResult);
				writer = PdfWriter.getInstance(document, pdfOutputStream);
				document.open();
				XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(pdfPath + "/temp.html"));
				System.out.println("Completed. PDF is available @ " + pdfFullPath);
			} catch (TransformerConfigurationException e) {
				e.getMessage();
			} catch (FileNotFoundException e) {
				e.getMessage();
			} catch (TransformerException e) {
				e.getMessage();
			} catch (DocumentException e) {
				e.getMessage();
			} catch (IOException e) {
				e.getMessage();
			} finally {
				if(document != null)
					document.close();
				if (writer != null)
					writer.close();
			}
		} else {
			throw new IllegalArgumentException("Input XML and Output PDF Path with filename is not provided as an argument");
		}
	}
}

