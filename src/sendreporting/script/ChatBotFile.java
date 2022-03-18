/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sendreporting.script;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Document;

import sendreporting.conec.Conect;
import sendreporting.entity.ContratEntity;
import sendreporting.entity.DocumentEntity;
import sendreporting.entity.TelechargementEntity;
import sendreporting.entity.TransactionsEntity;

/**
 *
 * @author Caitson Canpintam <caitson.canpintam@orange-sonatel.com>
 */
public class ChatBotFile {

    Connection con = null;

    //String sqlInfoContrat = "SELECT * FROM barkafon.notification as n, barkafon.contrats as c where date_notif = DATE ( NOW()) and type_archive = 'Contrat' and n.id_contrat = c.id;"; /////
    String sqlInfoContrat = "SELECT * FROM documents where date_alerte = DATE ( NOW());";

    //String sqlInfoDocument = "SELECT * FROM barkafon.notification as n, barkafon.documents as d where date_notif = DATE ( NOW()) and type_archive = 'autres-documents' \n"
            //+ "and n.id_document = d.id;"; ///////////////
    String sqlInfoDocument = "SELECT * FROM contrats where date_alerte = DATE ( NOW());";

    String sqlEmails = "SELECT email FROM orangemobile.email where etat=1;";
    String sqlTelechargementHistorique = "select sum(telecharge_android) as telecharge_android, sum(telechargement_ios) as telechargement_ios, \n"
            + "sum(premiere_connexion) as premiere_connexion, data_action  from (\n"
            + "SELECT left(date_insertion, 10) as data_action, 0 as  telecharge_android, count(*) as telechargement_ios,\n"
            + "count(*) as premiere_connexion\n"
            + "FROM orangemobile.login_logs where device_os='iOS'\n"
            + "AND date_format(subdate(now(), 1), '%Y-%m-%d 00:00:00')<=date_format(date_insertion, '%Y-%m-%d %H:%i:%s') \n"
            + "AND date_format(date_insertion, '%Y-%m-%d %H:%i:%s')<date_format(NOW(), '%Y-%m-%d 00:00:00') \n"
            + "group by left(date_insertion, 10), device_os \n"
            + "UNION\n"
            + "SELECT left(date_insertion, 10), count(*) as telecharge_android, 0 as telechargement_ios,\n"
            + "count(*) as premiere_connexion\n"
            + "FROM orangemobile.login_logs where device_os='Android'\n"
            + "AND date_format(subdate(now(), 1), '%Y-%m-%d 00:00:00')<=date_format(date_insertion, '%Y-%m-%d %H:%i:%s') \n"
            + "AND date_format(date_insertion, '%Y-%m-%d %H:%i:%s')<date_format(NOW(), '%Y-%m-%d 00:00:00') \n"
            + "group by left(date_insertion, 10), device_os \n"
            + ") as data_action\n"
            + "group by data_action order by data_action desc";
    String sqlTransactionsHistoriques = "SELECT left(u.date_action,10) as date_action, any_value(u.service) as service, any_value(n.description) as description, count(u.id) as nbre_transaction, \n"
            + "sum(if(amount is null, 0, amount)) as montant \n"
            + "FROM orangemobile.service_usage_logs u, orangemobile.service_name n \n"
            + "WHERE  u.service=n.name AND n.niveau_pertinence<=3 AND (status is null or status='SUCCEED')AND date_format(subdate(now(), 1), '%Y-%m-%d 00:00:00')<=date_format(u.date_action, '%Y-%m-%d %H:%i:%s') \n"
            + "AND date_format(u.date_action, '%Y-%m-%d %H:%i:%s')<date_format(NOW(), '%Y-%m-%d 00:00:00') \n"
            + "group by left(date_action,10), service, description \n"
            + "order by left(date_action,10) desc, montant desc, nbre_transaction desc;";

    public List<ContratEntity> listContrats() throws SQLException {
        con = Conect.getConexao();
        ContratEntity contratHisEntity;
        PreparedStatement psHisot = con.prepareStatement(sqlInfoContrat);
        ResultSet rs = psHisot.executeQuery();
        ArrayList<ContratEntity> lista = new ArrayList<>();
        while (rs.next()) {
            contratHisEntity = new ContratEntity();
            contratHisEntity.setNomContractant(rs.getString("nom_cocontractant"));
            contratHisEntity.setLieuExecution(rs.getString("lieu_execution"));
            contratHisEntity.setMontant(rs.getString("montant"));
            contratHisEntity.setDateSignature(rs.getString("date_signature"));
            contratHisEntity.setDateFin(rs.getString("date_fin"));
            contratHisEntity.setReference(rs.getString("reference"));
            contratHisEntity.setType(rs.getString("type"));
            contratHisEntity.setSousTypologie(rs.getString("sous_typologie"));
            contratHisEntity.setIntitule(rs.getString("intitule"));
            contratHisEntity.setObjetEmail(rs.getString("objet_email"));
            contratHisEntity.setListeAdresse(rs.getString("liste_adresse"));
            contratHisEntity.setContenu(rs.getString("contenu"));
            contratHisEntity.setDateNotif(rs.getString("date_notif"));
            contratHisEntity.setDatePremierNotif(rs.getString("date_premier_notif"));
            contratHisEntity.setDateSecondNotif(rs.getString("date_second_notif"));
            contratHisEntity.setEtat(rs.getString("etat"));
            contratHisEntity.setEtatNotif(rs.getString("etat_notif"));
            lista.add(contratHisEntity);
        }
        return lista;
    }

    public List<DocumentEntity> listDocuments() throws SQLException {
        con = Conect.getConexao();
        DocumentEntity documentHisEntity;
        PreparedStatement psHisot = con.prepareStatement(sqlInfoDocument);
        ResultSet rs = psHisot.executeQuery();
        ArrayList<DocumentEntity> lista = new ArrayList<>();
        while (rs.next()) {
            documentHisEntity = new DocumentEntity();
            documentHisEntity.setNomDocument(rs.getString("nom_document"));
            documentHisEntity.setObjetDocument(rs.getString("objet_document"));
            documentHisEntity.setTypeDocument(rs.getString("type_document"));
            documentHisEntity.setDelaiReponse(rs.getString("delai_reponse"));
            documentHisEntity.setDateNotification(rs.getString("date_notification"));
            documentHisEntity.setDateAlerte(rs.getString("date_alerte"));
            documentHisEntity.setIntitule(rs.getString("intitule"));
            documentHisEntity.setObjetEmail(rs.getString("objet_email"));
            documentHisEntity.setListeAdresse(rs.getString("liste_adresse"));
            documentHisEntity.setContenu(rs.getString("contenu"));
            documentHisEntity.setDateNotif(rs.getString("date_notif"));
            documentHisEntity.setDatePremierNotif(rs.getString("date_premier_notif"));
            documentHisEntity.setDateSecondNotif(rs.getString("date_second_notif"));
            documentHisEntity.setEtat(rs.getString("etat"));
            documentHisEntity.setEtatNotif(rs.getString("etat_notif"));
            lista.add(documentHisEntity);
        }
        return lista;
    }

    /*
	 * public List<String> getNomDocument() throws SQLException {
	 * 
	 * con = Conect.getConexao(); PreparedStatement psEmail =
	 * con.prepareStatement(sqlNomDocument); ResultSet rs = psEmail.executeQuery();
	 * ArrayList<String> lista = new ArrayList<>(); while(rs.next()) {
	 * lista.add(rs.getString("nom_document")); } //
	 * lista.add("doumbia.alassane@orange-sonatel.com"); //
	 * lista.add("Caitson.Canpintam@orange-sonatel.com"); return lista; }
	 * 
	 * public List<String> getEmails() throws SQLException {
	 * 
	 * con = Conect.getConexao(); PreparedStatement psEmail =
	 * con.prepareStatement(sqlAllEmails); ResultSet rs = psEmail.executeQuery();
	 * ArrayList<String> lista = new ArrayList<>(); while(rs.next()) {
	 * lista.add(rs.getString("liste_adresse")); } //
	 * lista.add("doumbia.alassane@orange-sonatel.com"); //
	 * lista.add("Caitson.Canpintam@orange-sonatel.com"); return lista; }
     */
 /*
	 * public List<ContratEntity> getInfoContrat() throws SQLException {
	 * 
	 * con = Conect.getConexao(); ContratEntity contratEntity; PreparedStatement
	 * psEmail = con.prepareStatement(sqlNotifDataNow); ResultSet rs =
	 * psEmail.executeQuery(); ArrayList<ContratEntity> lista = new ArrayList<>();
	 * while(rs.next()) {
	 * contratEntity.setNomContractant(rs.getString("nom_contractant"));
	 * contratEntity.setLieuExecution(rs.getString("lieu_execution"));
	 * contratEntity.setMontant(rs.getString("montant"));
	 * contratEntity.setDateSignature(rs.getString("date_signature"));
	 * contratEntity.setDateFin(rs.getString("date_fin"));
	 * contratEntity.setType(rs.getString("type"));
	 * contratEntity.setSousTypologie(rs.getString("sous_typologie"));
	 * contratEntity.setReference(rs.getString("reference"));
	 * lista.add(contratEntity); } return lista; }
     */
    public List<ContratEntity> getInfoContrat() throws SQLException {
        con = Conect.getConexao();
        ContratEntity contratEntity = new ContratEntity();
        PreparedStatement psHisot = con.prepareStatement(sqlInfoContrat);
        ResultSet rs = psHisot.executeQuery();
        ArrayList<ContratEntity> lista = new ArrayList<>();
        while (rs.next()) {
            contratEntity.setNomContractant(rs.getString("nom_contractant"));
            contratEntity.setLieuExecution(rs.getString("lieu_execution"));
            contratEntity.setMontant(rs.getString("montant"));
            contratEntity.setDateSignature(rs.getString("date_signature"));
            contratEntity.setDateFin(rs.getString("date_fin"));
            contratEntity.setType(rs.getString("type"));
            contratEntity.setSousTypologie(rs.getString("sous_typologie"));
            contratEntity.setReference(rs.getString("reference"));
            lista.add(contratEntity);
        }
        return lista;
    }

    public List<DocumentEntity> getInfoDocument() throws SQLException {
        con = Conect.getConexao();
        DocumentEntity documentEntity = new DocumentEntity();
        PreparedStatement psHisot = con.prepareStatement(sqlInfoDocument);
        ResultSet rs = psHisot.executeQuery();
        ArrayList<DocumentEntity> lista = new ArrayList<>();
        while (rs.next()) {
            documentEntity.setNomDocument(rs.getString("nom_document"));
            documentEntity.setObjetDocument(rs.getString("objet_document"));
            documentEntity.setTypeDocument(rs.getString("type_document"));
            documentEntity.setDateNotification(rs.getString("date_notification"));
            documentEntity.setDelaiReponse(rs.getString("delai_reponse"));
            documentEntity.setDateAlerte(rs.getString("date_alerte"));
            lista.add(documentEntity);
        }
        return lista;
    }

    public List<TransactionsEntity> listTransactions() throws SQLException {
        con = Conect.getConexao();
        TransactionsEntity transEntity;
        PreparedStatement psHisots = con.prepareStatement(sqlTransactionsHistoriques);
        ResultSet rs = psHisots.executeQuery();
        ArrayList<TransactionsEntity> lista = new ArrayList<>();
        while (rs.next()) {
            transEntity = new TransactionsEntity();
            transEntity.setDate_action(rs.getString("date_action"));
            transEntity.setService(rs.getString("service"));
            transEntity.setDescription(rs.getString("description"));
            transEntity.setNbre_transaction(rs.getString("nbre_transaction"));
            transEntity.setMontant(rs.getString("montant"));
            lista.add(transEntity);
        }
        return lista;
    }

    public List<String> getAllEamils() throws SQLException {

        con = Conect.getConexao();
        PreparedStatement psEmail = con.prepareStatement(sqlEmails);
        ResultSet rs = psEmail.executeQuery();
        ArrayList<String> lista = new ArrayList<>();
        while (rs.next()) {
            lista.add(rs.getString("email"));
        }
//        lista.add("doumbia.alassane@orange-sonatel.com");
//        lista.add("Caitson.Canpintam@orange-sonatel.com");
        return lista;
    }

}
