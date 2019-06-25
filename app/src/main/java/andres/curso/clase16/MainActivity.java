package andres.curso.clase16;

import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private TextView et_code, et_descri, et_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_code = findViewById(R.id.editCode);
        et_descri = findViewById(R.id.editDesc);
        et_price = findViewById(R.id.editPrice);

    }

    public void onSearch (View view){
        Resources res = getResources();
        String CodeNumber = et_code.getText().toString();

        AdminSqliteOpenHelper admin = new AdminSqliteOpenHelper( this, "Products" , null, 1 );
        SQLiteDatabase DataBase = admin.getWritableDatabase();

        if (!CodeNumber.isEmpty()){
           Cursor fila = DataBase.rawQuery("select Description, Price from articles where Code = " + CodeNumber, null);
           if (fila.moveToFirst()){
               et_descri.setText(fila.getString(0));
               et_price.setText(fila.getString(1));
           }else{
               Toast.makeText(this, res.getString(R.string.errorkeycodesearch),Toast.LENGTH_LONG).show();
               this.clearFields();
           }
           DataBase.close();
           fila.close();
        }else{
            Toast.makeText(this, res.getString(R.string.errorkeycode),Toast.LENGTH_LONG).show();
            this.clearFields();
        }
    }

    public  void clearFields (){
        et_code.setText("");
        et_descri.setText("");
        et_price.setText("");
    }
    public void onCreate (View view){
        AdminSqliteOpenHelper admin = new AdminSqliteOpenHelper( this, "Products" , null, 1 );
        SQLiteDatabase DataBase = admin.getWritableDatabase();
        String CodeNumber = et_code.getText().toString();
        String Description = et_descri.getText().toString();
        String Price = et_price.getText().toString();
        Resources res = getResources();

        if ( !CodeNumber.isEmpty() && !Description.isEmpty() && !Price.isEmpty()){
            ContentValues rowData = new ContentValues();
            rowData.put("Code" , CodeNumber) ;
            rowData.put("Description" , Description) ;
            rowData.put("Price" , Price) ;
            DataBase.insert("articles", null, rowData);
            DataBase.close();
            this.clearFields();
            Toast.makeText(this, res.getString(R.string.okayinsert),Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, res.getString(R.string.errorinsert),Toast.LENGTH_LONG).show();
        }
    }
}
