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
public class TelechargementEntity {
    
    private String telecharge_android;
    private String telechargement_ios;
    private String premiere_connexion;
    private String data_action;

    public String getTelecharge_android() {
        return telecharge_android;
    }

    public void setTelecharge_android(String telecharge_android) {
        this.telecharge_android = telecharge_android;
    }

    public String getTelechargement_ios() {
        return telechargement_ios;
    }

    public void setTelechargement_ios(String telechargement_ios) {
        this.telechargement_ios = telechargement_ios;
    }

    public String getPremiere_connexion() {
        return premiere_connexion;
    }

    public void setPremiere_connexion(String premiere_connexion) {
        this.premiere_connexion = premiere_connexion;
    }

    public String getData_action() {
        return data_action;
    }

    public void setData_action(String data_action) {
        this.data_action = data_action;
    }
    
    
}
