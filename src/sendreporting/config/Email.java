package sendreporting.config;

import java.io.File;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import sendreporting.entity.ContratEntity;
import sendreporting.entity.DocumentEntity;
import sendreporting.entity.TelechargementEntity;
import sendreporting.script.ChatBotFile;

/**
 *
 * @author Djaura100116
 */
public class Email implements Serializable {

	private static final String SMTP_SERVER = "webmail.orange-sonatel.com";
	private static final String SMTP_SERVER_HOLD = "10.137.19.176";
	private static final String SENDER = "noreply.nhaorange@orange-sonatel.com";
//    private static final String SENDER = "chatbot-maria@orange-bissau.com";
	private static final String EMAIL_TO = "caitson.canpintam@orange-sonatel.com";

	static ChatBotFile cbfile = new ChatBotFile();
	int indexCount = 0;

	public static boolean envoieDeMailExtended(List<String> emails) throws SQLException {

		ChatBotFile chatBootFile = new ChatBotFile();

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);

		String cc = "";

		String obj = "LEGAL TERM: Le " /* + chatBootFile.getTypeArchive() */;
		String body = "Bonjour,\r\n" + "Veiller trouver ci-apres le dossier pour traitement diligent,\n"
				+ "Tableau recap " + chatBootFile.getInfoDocument() + ".\n\n" + "nom contractant "
				+ chatBootFile.getInfoDocument() + ".\n\n" + "Cordialement, \n" + "DRSI/PSI";

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", SMTP_SERVER);
		Session session = Session.getInstance(properties);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SENDER));

			InternetAddress[] address = new InternetAddress[emails.size()];
			for (int i = 0; i < emails.size(); i++) {
				address[i] = new InternetAddress(emails.get(i));
			}
			message.addRecipients(Message.RecipientType.TO, address);

			if (!cc.equalsIgnoreCase("")) {
				message.setRecipients(Message.RecipientType.CC, cc);
			}

			message.setSubject(obj);
			BodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			messageBodyPart.setText(body);
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart, "text/html");
			if (!obj.equalsIgnoreCase("")) {
				Transport.send(message);
				System.out.println("message envoyé avec succès à --> " + Arrays.toString(address));
				return true;
			}
		} catch (MessagingException mex) {
			System.out.println("Error : " + mex);
			return false;
		}
		return false;
	}

	public static void envoiEmailContrat() throws SQLException {
		List<ContratEntity> histGroupByPhone = cbfile.listContrats();
		System.out.println("liste size" + histGroupByPhone.size());
		for (ContratEntity chatBotHisEntity : histGroupByPhone) {

			String cc = "";
			String obj = "LEGAL TERM: Le Contrat" /* + chatBootFile.getTypeArchive() */;
			String body = "Bonjour,\r\n" + "Veiller trouver ci-apres le dossier pour traitement diligent,\n"
					+ "Tableau recap: \n\n" + "Nom du contractant: " + chatBotHisEntity.getNomContractant() + ".\n\n"
					+ "Lieux d'execution: " + chatBotHisEntity.getLieuExecution() + ".\n\n" + "Montant du contrat: "
					+ chatBotHisEntity.getMontant() + ".\n\n" + "Date de signature: "
					+ chatBotHisEntity.getDateSignature() + ".\n\n" + "Date de fin: " + chatBotHisEntity.getDateFin()
					+ ".\n\n" + "Reference: " + chatBotHisEntity.getReference() + ".\n\n" + "Type de contrat: "
					+ chatBotHisEntity.getType() + ".\n\n" + "Sous type: " + chatBotHisEntity.getSousTypologie()
					+ ".\n\n" + "Cordialement, \n" + "DRSI/PSI";

			sendMail(chatBotHisEntity.getListeAdresse(), obj, body, cc);
			// return false;
		}

	}

	public static void envoiEmailDocument() throws SQLException {
		List<DocumentEntity> histGroupByPhone = cbfile.listDocuments();
		System.out.println("liste size" + histGroupByPhone.size());
		for (DocumentEntity chatBotHisEntity : histGroupByPhone) {

			String cc = "";
			String obj = "LEGAL TERM: Le Document" /* + chatBootFile.getTypeArchive() */;
			String body = "Bonjour,\r\n" + "Veiller trouver ci-apres le dossier pour traitement diligent,\n"
					+ "Tableau récap: \n\n" + "Nom du document: " + chatBotHisEntity.getNomDocument() + ".\n\n"
					+ "Objet du document: " + chatBotHisEntity.getObjetDocument() + ".\n\n" + "Type de document: "
					+ chatBotHisEntity.getTypeDocument() + ".\n\n" + "Delai de reponse: "
					+ chatBotHisEntity.getDelaiReponse() + ".\n\n" + "Date de notification: "
					+ chatBotHisEntity.getDateNotification() + ".\n\n" + "Date d'alerte: "
					+ chatBotHisEntity.getDateAlerte() + ".\n\n" + "Cordialement, \n" + "DRSI/PSI";
//            message.setContent("<h2>Bonjour " + infoName + ",</h2>"
//                    + "<p>Vous venez de commander les tickets restaurant pour le mois de " + dataComande + " </p>"
//                    + "<p>Ci-dessous les details de votre commande </p>"
//                    + "<p>- " + totalTicket + " </p>"
//                    + "<p>-  " + montante + "</p>"
//                    + "<p>Vous serez informe par la DR/SRH lorsque les tickets restaurant du mois sont disponibles!</p>",
//                    "text/html");
			sendMail(chatBotHisEntity.getListeAdresse(), obj, body, cc);
		}

	}

	public static void sendMail(String listeEmails, String obj, String body, String cc) {
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", SMTP_SERVER);
		Session session = Session.getInstance(properties);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SENDER));

			String[] emails = listeEmails.split(",");
			InternetAddress[] address = new InternetAddress[emails.length];
			for (int i = 0; i < emails.length; i++) {
				address[i] = new InternetAddress(emails[i]);
			}
			message.addRecipients(Message.RecipientType.TO, address);

			if (!cc.equalsIgnoreCase("")) {
				message.setRecipients(Message.RecipientType.CC, cc);
			}

			message.setSubject(obj);
			BodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();
			messageBodyPart.setText(body);
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart, "text/html");
			if (!obj.equalsIgnoreCase("")) {
				Transport.send(message);
				System.out.println("body� --> " + body);
				System.out.println("message envoyé avec succès à --> " + Arrays.toString(address));
				// return true;
			}
		} catch (MessagingException mex) {
			System.out.println("Error : " + mex);
			// return false;
		}
	}

	public static boolean envoieDeMail(String data, List<String> emails) {

		String histList = "Report_ChatBot_v2.xlsx";
		String histCount = "Actions_by_intent_v2.xlsx";
		String separdor = System.getProperty("file.separator");
		String raiz = new File("").getAbsolutePath().concat(separdor).concat("Orange").concat(separdor).concat("nha")
				.concat(separdor).concat("File_send".concat(separdor).concat(histList));

		String cc = "";
		String filePath = "/Users/alassanedoumbia/Documents/3b331e7a80944e77a1a8917311434092.jpg";
		String infoCont = "Bonjour,\n\nVeuillez trouver ci-joint le rapport journalieres  portant sur les stats de Nha Orange du "
				+ data + ".\n\nCordialement, \nDRSI/PSI";
//        String infoCont = "Bonjour,\n\nVeuillez trouver ci-joint le rapport des transactions journalieres  de service Chat bot du " + data + ".\n\nCordialement, \nDRSI/PSI";
		String obj = "RAPPORT DES TRANSACTIONS SERVICE CHATBOT DU " + data;

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", SMTP_SERVER);
		Session session = Session.getInstance(properties);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SENDER));

			InternetAddress[] address = new InternetAddress[emails.size()];
			for (int i = 0; i < emails.size(); i++) {
				address[i] = new InternetAddress(emails.get(i));
			}
			message.addRecipients(Message.RecipientType.TO, address);

			if (!cc.equalsIgnoreCase("")) {
				message.setRecipients(Message.RecipientType.CC, cc);
			}
			message.setSubject(obj);
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(infoCont);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			if (raiz != null && !raiz.isEmpty()) {
				DataSource source = new FileDataSource(raiz);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(filePath);
				multipart.addBodyPart(messageBodyPart);
				multipart.addBodyPart(messageBodyPart);
				message.setContent(multipart);
			}

			if (!obj.equalsIgnoreCase("")) {
				Transport.send(message);
				System.out.println("message envoyé avec succès --> " + Arrays.toString(address));
				return true;
			}
		} catch (MessagingException mex) {
			System.out.println("Error : " + mex);
			return false;
		}
		return false;
	}

	public String sendEmail(String infoName, String email, String dataComande, String totalTicket, String montante) {
//        String infoNameUper = infoName.charAt(0);
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", SMTP_SERVER);
		properties.put("mail.smtp.auth", "false");

		Session session = Session.getInstance(properties);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SENDER));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			message.setSubject("Notification " + dataComande + " Demande ticket restaurant!");

			message.setContent("<h2>Bonjour " + infoName + ",</h2>"
					+ "<p>Vous venez de commander les tickets restaurant pour le mois de " + dataComande + " </p>"
					+ "<p>Ci-dessous les details de votre commande </p>" + "<p>- " + totalTicket + " </p>" + "<p>-  "
					+ montante + "</p>"
					+ "<p>Vous serez informe par la DR/SRH lorsque les tickets restaurant du mois sont disponibles!</p>",
					"text/html");

			Transport.send(message);
			System.out.println("Email enviado com sucesso para : ( " + infoName + " )");
			return "Envoyé avec succès";
		} catch (AddressException e) {
			return "Erreur d'envoi, email invalide : " + e;
		} catch (MessagingException e) {
			return "Erreur d'envoi  : " + e;
		}

	}

	public String sendEmailCustomer(String email) {
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", SMTP_SERVER);
		properties.put("mail.smtp.auth", "false");

		Session session = Session.getInstance(properties);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SENDER));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			message.setSubject("Notification");

			message.setContent("<h2>Bonjour</h2>" + "<p>Vous venez de commander </p>"
					+ "<p>Ci-dessous les details de votre commande </p>" + "<tr>"
					+ "<td valign=?top? align=?center?><table width=?100%? cellspacing=?0? cellpadding=?0? border=?0? align=?center?>"
					+ "<tbody><tr>"
					+ "<td valign=?top? align=?center?><img class=?em_img? alt=?Welcome to EmailWeb Newsletter? style=?display:block; font-family:Arial, sans-serif; font-size:30px; line-height:34px; color:#000000; max-width:700px;? src=?Location of your image? width=?700? border=?0? height=?345?></td>"
					+ "</tbody></table></td>" + "</tr>" + "<p>-  totalTicket  </p>" + "<p>-   montante </p>"
					+ "<p>Vous serez informe par la DR/SRH lorsque les tickets restaurant du mois sont disponibles!</p>",
					"text/html");

			Transport.send(message);
			System.out.println("Email enviado com sucesso para : (infoName)");
			return "Envoyé avec succès";
		} catch (AddressException e) {
			return "Erreur d'envoi, email invalide : " + e;
		} catch (MessagingException e) {
			return "Erreur d'envoi  : " + e;
		}

	}
}
