package br.senai.sp.imc;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class IMCActivity extends AppCompatActivity {

    EditText etNome, etPeso, etAltura, etIdade;
    RadioGroup rgGrupo;
    RadioButton rbMasc, rbFem;
    Spinner spAtividade;
    Button btnCalcular, btnNovo;
    TextView tvImc, tvBasal, tvEnergia, tvPontos, tvTipo;
    LinearLayout llPrincipal;
    List<String> alTipos;
    String[] asTipos = {
            "Selecione",
            "Movimentado Com Atividade Física 1h",
            "Movimentado com Atividade Física 30min",
            "Movimentado com Atividade Física de 1h a 2,5h",
            "Movimentado com Atividade Física maior que 3h",
            "Movimentado sem Atividade Física",
            "Parado com Atividade Física 30min",
            "Sedentário com atividades"};

    float[][] vfValores = {
            {1.45f, 1.35f, 1.5f, 1.7f, 1.3f, 1.3f, 1.2f},
            {1.5f, 1.4f, 1.6f, 1.8f, 1.3f, 1.3f, 1.2f}
    };
    int iNumero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);
        etNome = findViewById(R.id.etNome);
        etPeso = findViewById(R.id.etPeso);
        etAltura = findViewById(R.id.etAltura);
        etIdade = findViewById(R.id.etIdade);
        rgGrupo = findViewById(R.id.rgGrupo);
        rbFem = findViewById(R.id.rbFem);
        rbMasc = findViewById(R.id.rbMasc);
        spAtividade = findViewById(R.id.spAtividade);
        btnCalcular = findViewById(R.id.btnCalcular);
        btnNovo = findViewById(R.id.btnNovo);
        tvBasal = findViewById(R.id.tvBasal);
        tvEnergia = findViewById(R.id.tvEnergia);
        tvImc = findViewById(R.id.tvImc);
        tvPontos = findViewById(R.id.tvPontos);
        tvTipo = findViewById(R.id.tvTipo);
        llPrincipal = findViewById(R.id.llPrincipal);
        tvBasal.setText(null);
        tvImc.setText(null);
        tvEnergia.setText(null);
        tvPontos.setText(null);
        tvTipo.setText(null);


        alTipos = new ArrayList<>();
        for (int i = 0; i < asTipos.length; i++) {
            alTipos.add(asTipos[i]);
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, alTipos);
        spAtividade.setAdapter(adaptador);

        btnNovo.setVisibility(View.INVISIBLE);

        spAtividade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                iNumero = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void calcular(View v) {
        if ((!etPeso.getText().toString().equals("") &&
                !etIdade.getText().toString().equals("") &&
                !etAltura.getText().toString().equals("") &&
                !etNome.getText().toString().equals("")) &&
                (rbFem.isChecked() || rbMasc.isChecked()) &&
                iNumero != 0) {

            float fPeso = Float.parseFloat(etPeso.getText().toString());
            float fAltura = Float.parseFloat(etAltura.getText().toString());
            float fIdade = Float.parseFloat(etIdade.getText().toString());
            float fSoma = 0f, fEnergia;
            float fImc = fPeso / (fAltura * fAltura);

            tvImc.setText(etNome.getText().toString() + " Seu IMC é: " + String.valueOf(fImc));
            if (fImc < 17) {
                llPrincipal.setBackgroundColor(Color.rgb(255, 20, 147));
                tvTipo.setText("MUITO ABAIXO do peso");
            } else if (fImc <= 18.4) {
                llPrincipal.setBackgroundColor(Color.rgb(000, 255, 000));
                tvTipo.setText("Abaixo do peso");
            } else if (fImc <= 24.9) {
                llPrincipal.setBackgroundColor(Color.rgb(000, 000, 255));
                tvTipo.setText("Normal");
            } else if (fImc <= 29.9) {
                llPrincipal.setBackgroundColor(Color.rgb(160, 32, 240));
                tvTipo.setText("Acima do Peso");
            } else if (fImc <= 34.9) {
                llPrincipal.setBackgroundColor(Color.YELLOW);
                tvTipo.setText("Obesidade Nivel 1");
            } else if (fImc <= 39.9) {
                llPrincipal.setBackgroundColor(Color.rgb(255, 69, 0));
                tvTipo.setText("Obesidade Nivel 2");
            } else {
                llPrincipal.setBackgroundColor(Color.rgb(255, 000, 000));
                tvTipo.setText("OBESIDADE NIVEL 3");
            }


            if (rbFem.isChecked()) {
                if (fIdade <= 18) {
                    fSoma = 12.2f * fPeso + 746;
                } else if (fIdade <= 30) {
                    fSoma = 14.7f * fPeso + 496;
                } else if (fIdade <= 60) {
                    fSoma = 8.7f * fPeso + 829;
                } else {
                    fSoma = 10.5f * fPeso + 596;
                }
                fEnergia = fSoma * vfValores[0][iNumero - 1];
            } else {
                if (fIdade <= 18) {
                    fSoma = 17.5f * fPeso + 651;
                } else if (fIdade <= 30) {
                    fSoma = 15.3f * fPeso + 679;
                } else if (fIdade <= 60) {
                    fSoma = 8.7f * fPeso + 879;
                } else {
                    fSoma = 13.5f * fPeso + 487;
                }
                fEnergia = fSoma * vfValores[1][iNumero - 1];
            }

            tvBasal.setText("Seu metabolismo basal é" + fSoma);
            tvEnergia.setText("a quantidade de energia necessária é de " + String.valueOf(fEnergia));
            tvPontos.setText("e seu regime por pontos deve ser de " + String.valueOf(fEnergia / 3.6));
            etNome.setEnabled(false);
            etPeso.setEnabled(false);
            etAltura.setEnabled(false);
            etIdade.setEnabled(false);
            btnCalcular.setEnabled(false);
            rbFem.setEnabled(false);
            rbMasc.setEnabled(false);
            spAtividade.setEnabled(false);
            btnNovo.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "Você deve preencher todos os campos", Toast.LENGTH_LONG).show();
        }
    }

    public void novo(View v) {
        etPeso.setEnabled(true);
        etAltura.setEnabled(true);
        etNome.setEnabled(true);
        etIdade.setEnabled(true);
        rbFem.setEnabled(true);
        rbMasc.setEnabled(true);
        spAtividade.setEnabled(true);
        etPeso.setText(null);
        etAltura.setText(null);
        etNome.setText(null);
        etIdade.setText(null);

        etNome.requestFocus();

        btnNovo.setVisibility(View.INVISIBLE);
        btnCalcular.setEnabled(true);
        rgGrupo.clearCheck();
        spAtividade.setSelection(0);

        tvImc.setText(null);
        tvTipo.setText(null);
        tvPontos.setText(null);
        tvEnergia.setText(null);
        tvBasal.setText(null);
        llPrincipal.setBackgroundColor(Color.WHITE);
    }
}
