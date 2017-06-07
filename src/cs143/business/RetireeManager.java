package cs143.business;

import cs143.dataaccess.SsnMap;
import cs143.domain.Retiree;

public class RetireeManager {

    //declare Ssn map variable to control handle the manager
    private SsnMap map;
    
    /**
     * Constructor for the retiree manager 
     * Create a new SSN map to handle the
     * the manager
     */
    public RetireeManager() {
        map = new SsnMap();
    }

    /**
     * Manage the add feature of the program based on the data of object retiree
     * @param retiree the retiree which the user want to add
     * @return true if adding is successful
     *         false if adding fails
     */
    public boolean add(Retiree retiree) {
        //Call the method insert in the Ssnmap class
        return map.insert(retiree.getSsn(), retiree);
    }

    /**
     * Delete the retiree object in the database 
     * @param ssn the social security number of the retiree user want to delete
     * @return true the deletion is successful
     *         false if the deletion fail
     */
    public boolean delete(long ssn) {
        //Call the method remove in the Ssnmap class
        return map.remove(ssn);
    }
    
    /**
     * get the information of the retiree with the specified SSN
     * @param ssn the social security number user want to get information
     * @return the object of the retiree with the specified SSN.
     *         null if the retiree is not found with the specified SSN
     */
    public Retiree get(long ssn) {
        //call the get method from the Ssnmap
        return map.get(ssn);
    }

}
