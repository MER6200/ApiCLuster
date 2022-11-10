import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import java.util.ArrayList;


public class ExcelReader {

    private int rows;
    private int columns;
    private ArrayList<Address> adr = new ArrayList<>();

    public ArrayList<Address> getAdr() {
        return adr;
    }

    public void setAdr(ArrayList<Address> adr) {
        this.adr = adr;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void read(String path) throws Exception {

        Workbook wb =  new Workbook(path);
        Worksheet worksheet = wb.getWorksheets().get(0);
         this.rows = worksheet.getCells().getMaxDataRow();
         this.columns = worksheet.getCells().getMaxDataColumn();

        System.out.println("Le nombre de ligne est de :"+rows+"\net il y a "+columns+" colognes.");
        for (int i = 1; i < 101; i++) {

            // Boucle sur chaque colonne de la ligne sélectionnée
            Address address = new Address();
            for (int j = 0; j < columns +1; j++) {

                if(j==0){

                    String nom = worksheet.getCells().get(i, j).getValue().toString();
                    address.setStreet_hint(nom);

                } else if (j==1) {

                    address.setLat(worksheet.getCells().get(i, j).getDoubleValue());

                } else if (j==2) {

                    address.setLon(worksheet.getCells().get(i, j).getDoubleValue());

                }

            }
            this.adr.add(address);
        }
    }
}
