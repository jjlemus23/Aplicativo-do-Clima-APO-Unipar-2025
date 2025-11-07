package com.example.apoclima;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class HomeActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private FloatingActionButton fabScanQr;

    // --- DADOS DA CIDADE CENTRALIZADOS ---
    // Mantém o estado da cidade na Activity para garantir que os Fragments sempre usem o valor mais recente.
    private String currentCity = "Sao Paulo"; // Cidade inicial padrão
    private LatLng currentLatLng = new LatLng(-23.55052, -46.633309); // Coordenadas padrão

    // Launcher para o resultado do scanner de QR Code
    private final ActivityResultLauncher<ScanOptions> qrCodeLauncher = registerForActivityResult(
            new ScanContract(),
            result -> {
                if (result.getContents() == null) {
                    Toast.makeText(HomeActivity.this, "Leitura de QR Code cancelada", Toast.LENGTH_LONG).show();
                } else {
                    processScannedQrCode(result.getContents());
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Aplicativo do Clima");
        }

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        fabScanQr = findViewById(R.id.fab_scan_qr);

        setupViewPager();

        fabScanQr.setOnClickListener(v -> startQrCodeScanner());
    }
    
    // Método para configurar o ViewPager e as abas.
    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Hoje");
                            break;
                        case 1:
                            tab.setText("Próximos Dias");
                            break;
                    }
                }).attach();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sobre) {
            SobreFragment sobreFragment = new SobreFragment();
            sobreFragment.show(getSupportFragmentManager(), "SobreFragment");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startQrCodeScanner() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Aponte para o QR Code de uma cidade");
        options.setBeepEnabled(true);
        options.setOrientationLocked(false);
        options.setCaptureActivity(com.journeyapps.barcodescanner.CaptureActivity.class);
        qrCodeLauncher.launch(options);
    }

    private void processScannedQrCode(String qrContent) {
        try {
            String[] parts = qrContent.split(";");
            if (parts.length == 3) {
                // 1. Atualiza os dados centralizados na Activity
                this.currentCity = parts[0].trim();
                double latitude = Double.parseDouble(parts[1].trim());
                double longitude = Double.parseDouble(parts[2].trim());
                this.currentLatLng = new LatLng(latitude, longitude);

                Toast.makeText(this, "Cidade atualizada: " + this.currentCity, Toast.LENGTH_LONG).show();

                // 2. Força a recriação dos fragments.
                // Esta é a forma mais simples e robusta de garantir que todos os fragments
                // usem os novos dados, pois eles serão recriados e buscarão os dados da Activity.
                setupViewPager();

            } else {
                Toast.makeText(this, "Conteúdo do QR Code inválido. Formato esperado: Cidade;Latitude;Longitude", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro ao processar QR Code: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    // --- GETTERS PÚBLICOS ---
    // Métodos para que os Fragments possam "puxar" os dados da cidade.
    public String getCurrentCity() {
        return currentCity;
    }

    public LatLng getCurrentLatLng() {
        return currentLatLng;
    }
}
