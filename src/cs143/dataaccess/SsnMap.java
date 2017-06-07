
package cs143.dataaccess;

import cs143.domain.Retiree;

public class SsnMap {

    private static int CAPACITY = 1024;
    private IDataAccess data = new DataAccessFileImpl();

    public Retiree get(long ssn) {
       SsnAvl tree = data.retrieveAvl(hash(ssn));
       return tree.get(ssn);
    }

    public boolean insert(long ssn, Retiree r) {
        SsnAvl tree = data.retrieveAvl(hash(ssn));
        if(tree.add(r)) {
            data.saveAvl(hash(ssn), tree);
            return true;
        }
        return false;
    }

    public boolean remove(long ssn) {
        SsnAvl tree = data.retrieveAvl(hash(ssn));
        if(tree.remove(get(ssn))) {
            data.saveAvl(hash(ssn), tree);
            return true;
        }
        return false;
    }
    
    public int hash(long ssn) {
        int hashCode = (int) (ssn ^ (ssn >>> 32));
        return supplementalHash(hashCode) & (CAPACITY - 1);
    }

    private static int supplementalHash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

}
