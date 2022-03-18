package sendreporting.entity;


public class NotifierEntity {
	
    private Long id;
	private String intitule;
	private String objetEmail;
	private String listeAdresse;
	private String contenu;
	private String dateNotif;
	private String datePremierNotif;
	private String dateSecondNotif;
	private String etat;
	private ContratEntity contrat;
	private DocumentEntity document;
	private String type_archive;
	
		
	public ContratEntity getContrat() {
		return contrat;
	}
	public void setContrat(ContratEntity contrat) {
		this.contrat = contrat;
	}
	public DocumentEntity getDocument() {
		return document;
	}
	public void setDocument(DocumentEntity document) {
		this.document = document;
	}
	public String getIntitule() {
		return intitule;
	}
	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	public String getObjetEmail() {
		return objetEmail;
	}
	public void setObjetEmail(String objetEmail) {
		this.objetEmail = objetEmail;
	}
	public String getListeAdresse() {
		return listeAdresse;
	}
	public void setListeAdresse(String listeAdresse) {
		this.listeAdresse = listeAdresse;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDateNotif() {
		return dateNotif;
	}
	public void setDateNotif(String dateNotif) {
		this.dateNotif = dateNotif;
	}
	public String getDatePremierNotif() {
		return datePremierNotif;
	}
	public void setDatePremierNotif(String datePremierNotif) {
		this.datePremierNotif = datePremierNotif;
	}
	public String getDateSecondNotif() {
		return dateSecondNotif;
	}
	public void setDateSecondNotif(String dateSecondNotif) {
		this.dateSecondNotif = dateSecondNotif;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public String getType_archive() {
		return type_archive;
	}
	public void setType_archive(String type_archive) {
		this.type_archive = type_archive;
	}
	
	
	

}
