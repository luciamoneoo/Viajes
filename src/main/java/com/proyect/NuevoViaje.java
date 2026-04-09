package com.proyect;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NuevoViaje extends JDialog {

    private GestorViajes gestor;

    private JTextField campoNombre;
    private JTextField campoDestino;
    private JTextField campoPersonas;
    private JTextField campoPrecio;
    private JTextField campoDias;
    private JTextField campoTransporte;
    private JTextField campoAlojamiento;
    private JComboBox<String> comboTipo;

    public NuevoViaje(JFrame padre, GestorViajes gestor) {
        super(padre, "Nuevo viaje", true); // true = modal
        this.gestor = gestor;

        setSize(400, 350);
        setLocationRelativeTo(padre);
        construirDialogo();
    }

    private void construirDialogo() {
        JPanel panel = new JPanel(new GridLayout(9, 2, 5, 5));

        campoNombre      = new JTextField();
        campoDestino     = new JTextField();
        campoPersonas    = new JTextField();
        campoPrecio      = new JTextField();
        campoDias        = new JTextField();
        campoTransporte  = new JTextField();
        campoAlojamiento = new JTextField();
        comboTipo        = new JComboBox<>(new String[]{"Nacional", "Internacional"});

        panel.add(new JLabel("Nombre:"));
        panel.add(campoNombre);
        panel.add(new JLabel("Destino:"));
        panel.add(campoDestino);
        panel.add(new JLabel("Nº personas:"));
        panel.add(campoPersonas);
        panel.add(new JLabel("Precio base (€):"));
        panel.add(campoPrecio);
        panel.add(new JLabel("Días:"));
        panel.add(campoDias);
        panel.add(new JLabel("Transporte:"));
        panel.add(campoTransporte);
        panel.add(new JLabel("Alojamiento:"));
        panel.add(campoAlojamiento);
        panel.add(new JLabel("Tipo:"));
        panel.add(comboTipo);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        panel.add(btnGuardar);
        panel.add(btnCancelar);

        add(panel);

        btnGuardar.addActionListener(e -> guardarViaje());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void guardarViaje() {
        // validaciones
        String nombre = campoNombre.getText().trim();
        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{3,40}$")) {
            JOptionPane.showMessageDialog(this, "Nombre incorrecto, solo letras entre 3 y 40 caracteres");
            return;
        }

        String destino = campoDestino.getText().trim();
        if (!destino.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{3,30}$")) {
            JOptionPane.showMessageDialog(this, "Destino incorrecto, solo letras entre 3 y 30 caracteres");
            return;
        }

        int personas;
        try {
            personas = Integer.parseInt(campoPersonas.getText().trim());
            if (personas < 1 || personas > 50) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nº personas tiene que ser un número entre 1 y 50");
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(campoPrecio.getText().trim().replace(",", "."));
            if (precio < 0 || precio > 10000) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio incorrecto, tiene que ser entre 0 y 10000");
            return;
        }

        int dias;
        try {
            dias = Integer.parseInt(campoDias.getText().trim());
            if (dias < 1 || dias > 365) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Días tiene que ser un número entre 1 y 365");
            return;
        }

        String transporte = campoTransporte.getText().trim();
        if (transporte.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El transporte no puede estar vacío");
            return;
        }

        String alojamiento = campoAlojamiento.getText().trim();
        if (alojamiento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El alojamiento no puede estar vacío");
            return;
        }

        // creamos el viaje según el tipo seleccionado
        Viajes v;
        if (comboTipo.getSelectedIndex() == 0) {
            v = new ViajesNacionales(nombre, personas, destino, precio, dias, transporte, alojamiento);
        } else {
            v = new ViajesInternacionales(nombre, personas, destino, precio, dias, transporte, alojamiento);
        }

        gestor.añadirViaje(v);
        JOptionPane.showMessageDialog(this, "Viaje añadido con ID: " + v.getId());
        dispose(); // cerramos el dialogo
    }
}