package ocr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class ReadImages {

	public static void main(String[] args) throws InvalidPasswordException, IOException {	
		System.out.println(lerImagem("C:\\temp\\modelo grrf.png"));
//		System.out.println("########################################");
//		System.out.println(lerPdf("C:\\temp\\GRF_GUIA_DE_RECOLHIMENTO_DO_FGTS_GFIP_SE (1).pdf"));
	}
	
	public static String lerImagem(String pathOfImage) throws IOException {
		File pdfPath = new File(pathOfImage);
		ITesseract image = new Tesseract();
		try {
			String texto = image.doOCR(pdfPath);
			return texto;
		} catch (TesseractException e) {
			return "Falha na leitura";
		}
	}
	
	public static String lerPdf(String pathOfPdf) throws IOException {
		Path pdfPath = Paths.get(pathOfPdf);
		byte[] pdf = Files.readAllBytes(pdfPath);

        try (PDDocument document = PDDocument.load( pdf )) {

            if (!document.isEncrypted()) {
			
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);

                PDFTextStripper tStripper = new PDFTextStripper();

                String pdfFileInText = tStripper.getText(document);
                return pdfFileInText;
            }
        } catch (IOException e) {
			return "Falha na leitura";
		}
		return pathOfPdf;
	}
}