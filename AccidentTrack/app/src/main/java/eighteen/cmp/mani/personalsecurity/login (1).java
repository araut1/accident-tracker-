public class Login {
    EditText e1,e2;
    Button b1,reg;
    String name,pass;
    public String NAME="name";
    protected void onCreate {
        setContentView(R.layout.login);
        e1=(EditText)findViewById(R.id.un);
        e2=(EditText)findViewById(R.id.pd);
        b1=(Button)findViewById(R.id.login);
        reg=(Button)findViewById(R.id.reg);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                name= e1.getText().toString();
                pass= e2.getText().toString();

                if(name.contentEquals("") && pass.contentEquals("")){

                    makeText(Login.this,"Enter the details").show();
                }else{
                    DatabaseHelper dbh = new DatabaseHelper(Login.this);
                    dbh.open();
                    String gpass= dbh.retrievepass(name);
                    if(pass.contentEquals(gpass)){
                        Intent i = new Intent(Login.this);
                        startActivity(i);
                        makeText(Login.this,"Login successfully...").show();
                        se.putString("name",e1.getText().toString());
                        se.apply();

                    }else{
                        makeText(Login.this,"Authentication Failure", LENGTH_LONG).show();
                    }
                }
                e1.setText("");
                e2.setText("");
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(Login.this);
                startActivity(i);
            }
        });
    }
}

