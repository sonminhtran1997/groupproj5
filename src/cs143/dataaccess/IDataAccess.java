package cs143.dataaccess;

public interface IDataAccess {
    
    boolean saveAvl(int index, SsnAvl avl);

    SsnAvl retrieveAvl(int index);
}
