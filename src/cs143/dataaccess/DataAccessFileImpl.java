package cs143.dataaccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataAccessFileImpl implements IDataAccess {
    
    @Override
    public boolean saveAvl(int index, SsnAvl avl) {
        String filename = "avl" + index + ".ser";
        try {
            ObjectOutputStream printer = new ObjectOutputStream(new FileOutputStream(new File(filename)));
            printer.writeObject(avl);
            printer.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    @Override
    public SsnAvl retrieveAvl(int index) {
        String filename = "avl" + index + ".ser";
        SsnAvl retirees;
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename));
            retirees = (SsnAvl) input.readObject();
            input.close();
        } catch (IOException | ClassNotFoundException ex) {
            retirees = new SsnAvl();  //no tree exists, create new
        }
        return retirees;
        
    }
    
}
