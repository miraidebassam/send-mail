/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sendreporting.entity;

/**
 *
 * @author alassanedoumbia
 */
public class TransactionsEntity {
    
    private String date_action;
    private String service;
    private String description;
    private String nbre_transaction;
    private String montant;

    public String getDate_action() {
        return date_action;
    }

    public void setDate_action(String date_action) {
        this.date_action = date_action;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNbre_transaction() {
        return nbre_transaction;
    }

    public void setNbre_transaction(String nbre_transaction) {
        this.nbre_transaction = nbre_transaction;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }
    
    
}
