package com.mycompany.examen_programacion2;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

public class Examen_Programacion2 {

    private static ArrayList<RentItem> items = new ArrayList<>();

    private static final Color BG_DARK      = new Color(40, 9, 5);
    private static final Color BG_PANEL     = new Color(116, 10, 3);
    private static final Color BG_CARD      = new Color(80, 7, 2);
    private static final Color ACCENT_BLUE  = new Color(195, 17, 12);
    private static final Color ACCENT_PINK  = new Color(230, 80, 27);
    private static final Color ACCENT_GREEN = new Color(230, 80, 27);
    private static final Color TEXT_WHITE   = new Color(255, 230, 220);
    private static final Color TEXT_GRAY    = new Color(200, 150, 130);
    private static final Font  FONT_TITLE   = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font  FONT_BTN     = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font  FONT_LABEL   = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font  FONT_SMALL   = new Font("Segoe UI", Font.BOLD, 12);

    private static CardLayout cardLayout = new CardLayout();
    private static JPanel     mainPanel  = new JPanel(cardLayout);
    private static JFrame     frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> iniciar());
    }

    private static void iniciar() {
        frame = new JFrame("Sistema de Renta Multimedia");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750, 620);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        mainPanel.setBackground(BG_DARK);
        mainPanel.add(panelMenu(),          "menu");
        mainPanel.add(panelAgregarMovie(),  "agregarMovie");
        mainPanel.add(panelAgregarGame(),   "agregarGame");
        mainPanel.add(panelRentar(),        "rentar");
        mainPanel.add(panelSubmenu(),       "submenu");
        mainPanel.add(panelImprimir(),      "imprimir");

        frame.add(mainPanel);
        cardLayout.show(mainPanel, "menu");
        frame.setVisible(true);
    }

    private static void ir(String vista) {
        if (vista.equals("imprimir")) refrescarImprimir();
        cardLayout.show(mainPanel, vista);
    }

    // Pregunta si es Movie o Game y navega al panel correcto
    private static void preguntarTipoAgregar() {
        String[] opciones = {"Movie (Pelicula)", "Game (Videojuego)"};
        int tipo = JOptionPane.showOptionDialog(frame,
            "Que deseas agregar?", "Agregar Item",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, opciones, opciones[0]);
        if (tipo == 0) ir("agregarMovie");
        else if (tipo == 1) ir("agregarGame");
    }

    // =========================================================================
    // MENU PRINCIPAL
    // =========================================================================
    private static JPanel panelMenu() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_DARK);

        JLabel titulo = new JLabel("RENTA MULTIMEDIA", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setForeground(ACCENT_BLUE);
        titulo.setOpaque(true);
        titulo.setBackground(BG_DARK);
        titulo.setBorder(new EmptyBorder(40, 0, 5, 0));

        JLabel sub = new JLabel("Sistema de gestion de peliculas y videojuegos", SwingConstants.CENTER);
        sub.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        sub.setForeground(TEXT_GRAY);
        sub.setOpaque(true);
        sub.setBackground(BG_DARK);
        sub.setBorder(new EmptyBorder(0, 0, 30, 0));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_DARK);
        header.add(titulo, BorderLayout.CENTER);
        header.add(sub,    BorderLayout.SOUTH);

        JPanel btns = new JPanel(new GridLayout(5, 1, 0, 14));
        btns.setBackground(BG_DARK);
        btns.setBorder(new EmptyBorder(0, 150, 50, 150));

        JButton bAgregar  = boton("Agregar Item",     ACCENT_BLUE);
        JButton bRentar   = boton("Rentar",            ACCENT_BLUE);
        JButton bSubmenu  = boton("Ejecutar Submenu",  ACCENT_BLUE);
        JButton bImprimir = boton("Imprimir Todo",     ACCENT_BLUE);
        JButton bSalir    = boton("Salir",             ACCENT_PINK);

        bAgregar .addActionListener(e -> preguntarTipoAgregar());
        bRentar  .addActionListener(e -> ir("rentar"));
        bSubmenu .addActionListener(e -> ir("submenu"));
        bImprimir.addActionListener(e -> ir("imprimir"));
        bSalir   .addActionListener(e -> System.exit(0));

        btns.add(bAgregar);
        btns.add(bRentar);
        btns.add(bSubmenu);
        btns.add(bImprimir);
        btns.add(bSalir);

        panel.add(header, BorderLayout.NORTH);
        panel.add(btns,   BorderLayout.CENTER);
        return panel;
    }

    // =========================================================================
    // AGREGAR MOVIE
    // =========================================================================
    private static JTextField   tfMovieCodigo, tfMovieNombre, tfMoviePrecio, tfMovieCopias;
    private static JLabel       lblMovieImg;
    private static ImageIcon    imagenMovie = null;
    private static JRadioButton rbEstreno, rbNormal;

    private static JPanel panelAgregarMovie() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_DARK);
        panel.add(headerVista("Agregar Pelicula", "menu"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(BG_PANEL);
        form.setBorder(new EmptyBorder(25, 50, 25, 50));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 6, 8, 6);
        g.fill   = GridBagConstraints.HORIZONTAL;

        tfMovieCodigo = campo();
        tfMovieNombre = campo();
        tfMoviePrecio = campo();
        tfMovieCopias = campo();

        // Estado
        rbEstreno = new JRadioButton("ESTRENO  (recargo +50 Lps/dia despues del dia 2)");
        rbNormal  = new JRadioButton("NORMAL   (recargo +30 Lps/dia despues del dia 5)");
        estilizarRadio(rbEstreno);
        estilizarRadio(rbNormal);
        rbEstreno.setSelected(true);
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbEstreno);
        bg.add(rbNormal);

        JPanel estadoPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        estadoPanel.setBackground(BG_PANEL);
        estadoPanel.add(rbEstreno);
        estadoPanel.add(rbNormal);

        // Imagen
        lblMovieImg = new JLabel("Sin imagen", SwingConstants.CENTER);
        lblMovieImg.setPreferredSize(new Dimension(80, 80));
        lblMovieImg.setBackground(BG_CARD);
        lblMovieImg.setForeground(TEXT_GRAY);
        lblMovieImg.setOpaque(true);
        lblMovieImg.setFont(FONT_SMALL);
        lblMovieImg.setBorder(BorderFactory.createLineBorder(ACCENT_BLUE, 1));

        JButton btnImg = botonPequeno("Cargar imagen", ACCENT_BLUE);
        btnImg.addActionListener(e -> {
            ImageIcon img = elegirImagen();
            if (img != null) {
                imagenMovie = img;
                Image scaled = img.getImage().getScaledInstance(78, 78, Image.SCALE_SMOOTH);
                lblMovieImg.setIcon(new ImageIcon(scaled));
                lblMovieImg.setText("");
            }
        });

        JPanel imgPanel = new JPanel(new BorderLayout(10, 0));
        imgPanel.setBackground(BG_PANEL);
        imgPanel.add(lblMovieImg, BorderLayout.WEST);
        imgPanel.add(btnImg,      BorderLayout.CENTER);

        int row = 0;
        agregarFila(form, g, row++, "Codigo:",      tfMovieCodigo);
        agregarFila(form, g, row++, "Nombre:",      tfMovieNombre);
        agregarFila(form, g, row++, "Precio base:", tfMoviePrecio);
        agregarFila(form, g, row++, "Copias:",      tfMovieCopias);
        agregarFila(form, g, row++, "Estado:",      estadoPanel);
        agregarFila(form, g, row++, "Imagen:",      imgPanel);

        JButton btnGuardar = boton("Guardar Pelicula", ACCENT_GREEN);
        btnGuardar.addActionListener(e -> guardarMovie());

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        footer.setBackground(BG_DARK);
        footer.add(btnGuardar);

        panel.add(new JScrollPane(form) {{ setBorder(BorderFactory.createEmptyBorder()); getViewport().setBackground(BG_PANEL); }}, BorderLayout.CENTER);
        panel.add(footer, BorderLayout.SOUTH);
        return panel;
    }

    private static void guardarMovie() {
        try {
            String codigo = tfMovieCodigo.getText().trim();
            String nombre = tfMovieNombre.getText().trim();
            String precioStr = tfMoviePrecio.getText().trim();
            String copiasStr = tfMovieCopias.getText().trim();

            if (codigo.isEmpty() || nombre.isEmpty() || precioStr.isEmpty() || copiasStr.isEmpty()) {
                throw new Exception("Todos los campos son obligatorios.");
            }
            for (RentItem r : items) {
                if (r.getCodigo().equalsIgnoreCase(codigo)) {
                    throw new Exception("El codigo '" + codigo + "' ya existe.");
                }
            }

            double precio = Double.parseDouble(precioStr);
            int    copias = Integer.parseInt(copiasStr);

            ClaseMovie movie = new ClaseMovie(codigo, nombre, precio);
            movie.setCopiasDisponibles(copias);
            movie.setEstadoManual(rbEstreno.isSelected() ? "ESTRENO" : "NORMAL");
            if (imagenMovie != null) movie.setImagen(imagenMovie);

            items.add(movie);
            JOptionPane.showMessageDialog(frame,
                "Pelicula '" + nombre + "' agregada.\nEstado: " + movie.getEstado(),
                "Exito", JOptionPane.INFORMATION_MESSAGE);

            limpiarMovie();
            ir("menu");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Precio y copias deben ser numeros validos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void limpiarMovie() {
        tfMovieCodigo.setText("");
        tfMovieNombre.setText("");
        tfMoviePrecio.setText("");
        tfMovieCopias.setText("");
        rbEstreno.setSelected(true);
        lblMovieImg.setIcon(null);
        lblMovieImg.setText("Sin imagen");
        imagenMovie = null;
    }

    // =========================================================================
    // AGREGAR GAME
    // =========================================================================
    private static JTextField tfGameCodigo, tfGameNombre, tfGameCopias, tfGamePlataforma;
    private static JTextArea  taGameSpecs;
    private static JLabel     lblGameImg;
    private static ImageIcon  imagenGame = null;

    private static JPanel panelAgregarGame() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_DARK);
        panel.add(headerVista("Agregar Videojuego", "menu"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(BG_PANEL);
        form.setBorder(new EmptyBorder(25, 50, 25, 50));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 6, 8, 6);
        g.fill   = GridBagConstraints.HORIZONTAL;

        tfGameCodigo    = campo();
        tfGameNombre    = campo();
        tfGameCopias    = campo();
        tfGamePlataforma= campo();

        // Specs
        taGameSpecs = new JTextArea(4, 1);
        taGameSpecs.setBackground(BG_CARD);
        taGameSpecs.setForeground(TEXT_WHITE);
        taGameSpecs.setCaretColor(TEXT_WHITE);
        taGameSpecs.setFont(FONT_LABEL);
        taGameSpecs.setBorder(new EmptyBorder(6, 8, 6, 8));
        taGameSpecs.setLineWrap(true);

        // Label de renta fija (no editable, solo informativo)
        JLabel lblRentaFija = new JLabel("20 Lps / dia  (fijo)");
        lblRentaFija.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblRentaFija.setForeground(ACCENT_GREEN);

        // Imagen
        lblGameImg = new JLabel("Sin imagen", SwingConstants.CENTER);
        lblGameImg.setPreferredSize(new Dimension(80, 80));
        lblGameImg.setBackground(BG_CARD);
        lblGameImg.setForeground(TEXT_GRAY);
        lblGameImg.setOpaque(true);
        lblGameImg.setFont(FONT_SMALL);
        lblGameImg.setBorder(BorderFactory.createLineBorder(ACCENT_PINK, 1));

        JButton btnImg = botonPequeno("Cargar imagen", ACCENT_PINK);
        btnImg.addActionListener(e -> {
            ImageIcon img = elegirImagen();
            if (img != null) {
                imagenGame = img;
                Image scaled = img.getImage().getScaledInstance(78, 78, Image.SCALE_SMOOTH);
                lblGameImg.setIcon(new ImageIcon(scaled));
                lblGameImg.setText("");
            }
        });

        JPanel imgPanel = new JPanel(new BorderLayout(10, 0));
        imgPanel.setBackground(BG_PANEL);
        imgPanel.add(lblGameImg, BorderLayout.WEST);
        imgPanel.add(btnImg,     BorderLayout.CENTER);

        int row = 0;
        agregarFila(form, g, row++, "Codigo:",            tfGameCodigo);
        agregarFila(form, g, row++, "Nombre:",            tfGameNombre);
        agregarFila(form, g, row++, "Renta:",             lblRentaFija);
        agregarFila(form, g, row++, "Copias:",            tfGameCopias);
        agregarFila(form, g, row++, "Plataforma:",        tfGamePlataforma);
        agregarFila(form, g, row++, "Specs (1 x linea):", new JScrollPane(taGameSpecs) {{ setBorder(BorderFactory.createEmptyBorder()); }});
        agregarFila(form, g, row++, "Imagen:",            imgPanel);

        JButton btnGuardar = boton("Guardar Videojuego", ACCENT_GREEN);
        btnGuardar.addActionListener(e -> guardarGame());

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        footer.setBackground(BG_DARK);
        footer.add(btnGuardar);

        panel.add(new JScrollPane(form) {{ setBorder(BorderFactory.createEmptyBorder()); getViewport().setBackground(BG_PANEL); }}, BorderLayout.CENTER);
        panel.add(footer, BorderLayout.SOUTH);
        return panel;
    }

    private static void guardarGame() {
        try {
            String codigo    = tfGameCodigo.getText().trim();
            String nombre    = tfGameNombre.getText().trim();
            String copiasStr = tfGameCopias.getText().trim();

            if (codigo.isEmpty() || nombre.isEmpty() || copiasStr.isEmpty()) {
                throw new Exception("Codigo, nombre y copias son obligatorios.");
            }
            for (RentItem r : items) {
                if (r.getCodigo().equalsIgnoreCase(codigo)) {
                    throw new Exception("El codigo '" + codigo + "' ya existe.");
                }
            }

            int copias = Integer.parseInt(copiasStr);

            Game game = new Game(codigo, nombre, 0);
            game.setCopiasDisponibles(copias);

            String plataforma = tfGamePlataforma.getText().trim();
            if (!plataforma.isEmpty()) game.setPlataforma(plataforma);

            String[] lineas = taGameSpecs.getText().split("\\n");
            for (String linea : lineas) {
                if (!linea.trim().isEmpty()) game.addEspecificacion(linea.trim());
            }

            if (imagenGame != null) game.setImagen(imagenGame);

            items.add(game);
            JOptionPane.showMessageDialog(frame,
                "Videojuego '" + nombre + "' agregado.",
                "Exito", JOptionPane.INFORMATION_MESSAGE);

            limpiarGame();
            ir("menu");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Copias debe ser un numero valido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void limpiarGame() {
        tfGameCodigo.setText("");
        tfGameNombre.setText("");
        tfGameCopias.setText("");
        tfGamePlataforma.setText("");
        taGameSpecs.setText("");
        lblGameImg.setIcon(null);
        lblGameImg.setText("Sin imagen");
        imagenGame = null;
    }

    // =========================================================================
    // RENTAR
    // =========================================================================
    private static JTextField tfCodigoRenta, tfDias;
    private static JPanel     panelInfoRenta;
    private static RentItem   itemRenta = null;

    private static JPanel panelRentar() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_DARK);
        panel.add(headerVista("Rentar Item", "menu"), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 20));
        centro.setBackground(BG_DARK);
        centro.setBorder(new EmptyBorder(25, 60, 25, 60));

        JPanel busqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        busqueda.setBackground(BG_DARK);
        tfCodigoRenta = campo();
        tfCodigoRenta.setPreferredSize(new Dimension(200, 35));
        JButton btnBuscar = botonPequeno("Buscar", ACCENT_BLUE);
        btnBuscar.addActionListener(e -> buscarParaRentar());
        busqueda.add(etiqueta("Codigo:"));
        busqueda.add(tfCodigoRenta);
        busqueda.add(btnBuscar);

        panelInfoRenta = new JPanel(new BorderLayout(15, 0));
        panelInfoRenta.setBackground(BG_CARD);
        panelInfoRenta.setBorder(new EmptyBorder(15, 15, 15, 15));
        panelInfoRenta.setVisible(false);

        JPanel rentaForm = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        rentaForm.setBackground(BG_DARK);
        tfDias = campo();
        tfDias.setPreferredSize(new Dimension(100, 35));
        JButton btnCalc = botonPequeno("Calcular renta", ACCENT_GREEN);
        btnCalc.addActionListener(e -> calcularRenta());
        rentaForm.add(etiqueta("Dias:"));
        rentaForm.add(tfDias);
        rentaForm.add(btnCalc);

        centro.add(busqueda,       BorderLayout.NORTH);
        centro.add(panelInfoRenta, BorderLayout.CENTER);
        centro.add(rentaForm,      BorderLayout.SOUTH);

        panel.add(centro, BorderLayout.CENTER);
        return panel;
    }

    private static void buscarParaRentar() {
        String cod = tfCodigoRenta.getText().trim();
        if (cod.isEmpty()) return;
        itemRenta = buscarPorCodigo(cod);
        panelInfoRenta.removeAll();
        if (itemRenta == null) {
            JOptionPane.showMessageDialog(frame, "Item No Existe", "Error", JOptionPane.ERROR_MESSAGE);
            panelInfoRenta.setVisible(false);
        } else {
            panelInfoRenta.add(imagenLabel(itemRenta, 90), BorderLayout.WEST);
            panelInfoRenta.add(infoLabel(itemRenta),       BorderLayout.CENTER);
            panelInfoRenta.setVisible(true);
        }
        panelInfoRenta.revalidate();
        panelInfoRenta.repaint();
    }

    private static void calcularRenta() {
        if (itemRenta == null) {
            JOptionPane.showMessageDialog(frame, "Primero busca un item.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int dias = Integer.parseInt(tfDias.getText().trim());
            if (dias <= 0) throw new NumberFormatException();
            double total = itemRenta.pagoRenta(dias);
            JOptionPane.showMessageDialog(frame,
                "Item: "  + itemRenta.getNombre() + "\n" +
                "Dias: "  + dias + "\n" +
                "Total: " + String.format("%.2f", total) + " Lps",
                "Resultado de Renta", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Ingresa un numero de dias valido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================================
    // SUBMENU (solo Game)
    // =========================================================================
    private static JTextField tfCodigoSub;
    private static JPanel     panelSubBotones;
    private static Game       gameSeleccionado = null;

    private static JPanel panelSubmenu() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_DARK);
        panel.add(headerVista("Ejecutar Submenu", "menu"), BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(0, 20));
        centro.setBackground(BG_DARK);
        centro.setBorder(new EmptyBorder(25, 100, 25, 100));

        JPanel busqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        busqueda.setBackground(BG_DARK);
        tfCodigoSub = campo();
        tfCodigoSub.setPreferredSize(new Dimension(200, 35));
        JButton btnBuscar = botonPequeno("Buscar Game", ACCENT_BLUE);
        btnBuscar.addActionListener(e -> buscarGame());
        busqueda.add(etiqueta("Codigo:"));
        busqueda.add(tfCodigoSub);
        busqueda.add(btnBuscar);

        panelSubBotones = new JPanel(new GridLayout(3, 1, 0, 12));
        panelSubBotones.setBackground(BG_DARK);
        panelSubBotones.setVisible(false);

        String[] ops = {
            "1. Actualizar fecha de publicacion",
            "2. Agregar especificacion",
            "3. Ver especificaciones"
        };
        for (int i = 0; i < ops.length; i++) {
            final int op = i + 1;
            JButton b = boton(ops[i], ACCENT_PINK);
            b.addActionListener(e -> ejecutarOpcionGame(op));
            panelSubBotones.add(b);
        }

        centro.add(busqueda,        BorderLayout.NORTH);
        centro.add(panelSubBotones, BorderLayout.CENTER);
        panel.add(centro, BorderLayout.CENTER);
        return panel;
    }

    private static void buscarGame() {
        String cod = tfCodigoSub.getText().trim();
        if (cod.isEmpty()) return;
        RentItem item = buscarPorCodigo(cod);
        if (item == null) {
            JOptionPane.showMessageDialog(frame, "Item No Existe", "Error", JOptionPane.ERROR_MESSAGE);
            panelSubBotones.setVisible(false);
        } else if (!(item instanceof MenuActions)) {
            JOptionPane.showMessageDialog(frame, "Solo los Games tienen submenu.", "Error", JOptionPane.ERROR_MESSAGE);
            panelSubBotones.setVisible(false);
        } else {
            gameSeleccionado = (Game) item;
            panelSubBotones.setVisible(true);
        }
        panelSubBotones.revalidate();
        panelSubBotones.repaint();
    }

    private static void ejecutarOpcionGame(int opcion) {
        if (gameSeleccionado == null) return;
        switch (opcion) {
            case 1:
                Calendar cal = pedirFechaDialog("Actualizar fecha de publicacion");
                if (cal != null) {
                    gameSeleccionado.setFechaPublicacion(
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH) + 1,
                        cal.get(Calendar.DAY_OF_MONTH));
                    JOptionPane.showMessageDialog(frame, "Fecha actualizada.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            case 2:
                String spec = JOptionPane.showInputDialog(frame, "Nueva especificacion:", "Agregar Especificacion", JOptionPane.PLAIN_MESSAGE);
                if (spec != null && !spec.trim().isEmpty()) {
                    gameSeleccionado.addEspecificacion(spec.trim());
                    JOptionPane.showMessageDialog(frame, "Especificacion agregada.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            case 3:
                JFrame specFrame = new JFrame("Especificaciones: " + gameSeleccionado.getNombre());
                specFrame.setSize(420, 360);
                specFrame.setLocationRelativeTo(frame);
                specFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                specFrame.getContentPane().setBackground(BG_DARK);

                JTextArea area = new JTextArea();
                area.setEditable(false);
                area.setBackground(BG_CARD);
                area.setForeground(TEXT_WHITE);
                area.setFont(new Font("Consolas", Font.PLAIN, 13));
                area.setBorder(new EmptyBorder(12, 12, 12, 12));

                // Capturar salida del metodo recursivo
                java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                java.io.PrintStream ps = new java.io.PrintStream(baos);
                java.io.PrintStream old = System.out;
                System.setOut(ps);
                gameSeleccionado.listEspecificaciones();
                System.out.flush();
                System.setOut(old);

                StringBuilder sb = new StringBuilder();
                sb.append("Especificaciones de: ").append(gameSeleccionado.getNombre()).append("\n");
                sb.append("----------------------------------------\n");
                sb.append(baos.toString().isEmpty() ? "Sin especificaciones registradas." : baos.toString());
                area.setText(sb.toString());

                JScrollPane scrollSpec = new JScrollPane(area);
                scrollSpec.setBorder(BorderFactory.createEmptyBorder());
                specFrame.add(scrollSpec);
                specFrame.setVisible(true);
                break;
        }
    }

    // =========================================================================
    // IMPRIMIR TODO
    // =========================================================================
    private static JPanel panelCards;

    private static JPanel panelImprimir() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_DARK);
        panel.add(headerVista("Todos los Items", "menu"), BorderLayout.NORTH);

        panelCards = new JPanel();
        panelCards.setLayout(new BoxLayout(panelCards, BoxLayout.Y_AXIS));
        panelCards.setBackground(BG_DARK);
        panelCards.setBorder(new EmptyBorder(10, 30, 20, 30));

        JScrollPane scroll = new JScrollPane(panelCards);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(BG_DARK);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private static void refrescarImprimir() {
        panelCards.removeAll();
        if (items.isEmpty()) {
            JLabel lbl = new JLabel("No hay items registrados.", SwingConstants.CENTER);
            lbl.setForeground(TEXT_GRAY);
            lbl.setFont(FONT_LABEL);
            panelCards.add(lbl);
        } else {
            for (RentItem item : items) {
                JPanel card = new JPanel(new BorderLayout(15, 0));
                card.setBackground(BG_CARD);
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 105));
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ACCENT_BLUE.darker(), 1),
                    new EmptyBorder(12, 15, 12, 15)));
                card.add(imagenLabel(item, 75), BorderLayout.WEST);
                card.add(infoLabel(item),       BorderLayout.CENTER);
                panelCards.add(card);
                panelCards.add(Box.createVerticalStrut(10));
            }
        }
        panelCards.revalidate();
        panelCards.repaint();
    }

    // =========================================================================
    // UTILIDADES DE UI
    // =========================================================================
    private static JLabel imagenLabel(RentItem item, int size) {
        JLabel lbl = new JLabel();
        lbl.setPreferredSize(new Dimension(size, size));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setOpaque(true);
        lbl.setBackground(BG_PANEL);
        Color borde = item instanceof ClaseMovie ? ACCENT_PINK : ACCENT_BLUE;
        lbl.setBorder(BorderFactory.createLineBorder(borde, 1));
        if (item.getImagen() != null) {
            Image img = item.getImagen().getImage().getScaledInstance(size - 2, size - 2, Image.SCALE_SMOOTH);
            lbl.setIcon(new ImageIcon(img));
        } else {
            lbl.setText(item instanceof ClaseMovie ? "MOVIE" : "GAME");
            lbl.setFont(FONT_SMALL);
            lbl.setForeground(TEXT_GRAY);
        }
        return lbl;
    }

    private static JLabel infoLabel(RentItem item) {
        String extra, color, precioInfo;
        if (item instanceof ClaseMovie) {
            extra     = "Pelicula  |  Estado: " + ((ClaseMovie) item).getEstado();
            color     = "#FF50A0";
            precioInfo= "Precio base: " + item.getPrecioBase() + " Lps";
        } else {
            extra     = "Videojuego  |  Plataforma: " + ((Game) item).getPlataforma();
            color     = "#508CFF";
            precioInfo= "Renta: 20 Lps/dia (fijo)";
        }
        JLabel lbl = new JLabel("<html>"
            + "<b style='font-size:13px'>" + item.getNombre() + "</b><br>"
            + "<font color='#9696B4'>Codigo: " + item.getCodigo()
            + "&nbsp;|&nbsp;Copias: " + item.getCopiasDisponibles()
            + "&nbsp;|&nbsp;" + precioInfo + "</font><br>"
            + "<font color='" + color + "'>" + extra + "</font>"
            + "</html>");
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXT_WHITE);
        return lbl;
    }

    private static JPanel headerVista(String titulo, String vistaVolver) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_PANEL);
        header.setBorder(new EmptyBorder(15, 20, 15, 20));
        JLabel lbl = new JLabel(titulo, SwingConstants.CENTER);
        lbl.setFont(FONT_TITLE);
        lbl.setForeground(ACCENT_BLUE);
        JButton btnVolver = botonPequeno("< Volver", ACCENT_PINK);
        btnVolver.addActionListener(e -> ir(vistaVolver));
        header.add(btnVolver, BorderLayout.WEST);
        header.add(lbl,       BorderLayout.CENTER);
        return header;
    }

    private static Calendar pedirFechaDialog(String titulo) {
        JSpinner sDia  = new JSpinner(new SpinnerNumberModel(1,    1,    31,   1));
        JSpinner sMes  = new JSpinner(new SpinnerNumberModel(1,    1,    12,   1));
        JSpinner sAnio = new JSpinner(new SpinnerNumberModel(2025, 1900, 2100, 1));
        sAnio.setEditor(new JSpinner.NumberEditor(sAnio, "#"));
        JPanel p = new JPanel(new GridLayout(3, 2, 8, 8));
        p.setBackground(BG_PANEL);
        p.add(etiqueta("Dia:"));  p.add(sDia);
        p.add(etiqueta("Mes:"));  p.add(sMes);
        p.add(etiqueta("Anio:")); p.add(sAnio);
        int r = JOptionPane.showConfirmDialog(frame, p, titulo, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (r != JOptionPane.OK_OPTION) return null;
        Calendar cal = Calendar.getInstance();
        cal.set((int) sAnio.getValue(), (int) sMes.getValue() - 1, (int) sDia.getValue());
        return cal;
    }

    private static ImageIcon elegirImagen() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imagenes", "jpg", "jpeg", "png", "gif"));
        return chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION
            ? new ImageIcon(chooser.getSelectedFile().getAbsolutePath())
            : null;
    }

    private static RentItem buscarPorCodigo(String codigo) {
        for (RentItem r : items)
            if (r.getCodigo().equalsIgnoreCase(codigo)) return r;
        return null;
    }

    private static JTextField campo() {
        JTextField tf = new JTextField();
        tf.setBackground(BG_CARD);
        tf.setForeground(TEXT_WHITE);
        tf.setCaretColor(TEXT_WHITE);
        tf.setFont(FONT_LABEL);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_BLUE.darker(), 1),
            new EmptyBorder(5, 8, 5, 8)));
        return tf;
    }

    private static JLabel etiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(TEXT_WHITE);
        lbl.setFont(FONT_LABEL);
        return lbl;
    }

    private static JButton boton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(FONT_BTN);
        btn.setForeground(TEXT_WHITE);
        btn.setBackground(BG_CARD);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 1),
            new EmptyBorder(10, 20, 10, 20)));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(color.darker()); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(BG_CARD); }
        });
        return btn;
    }

    private static JButton botonPequeno(String texto, Color color) {
        JButton btn = boton(texto, color);
        btn.setFont(FONT_SMALL);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 1),
            new EmptyBorder(6, 12, 6, 12)));
        return btn;
    }

    private static void estilizarRadio(JRadioButton rb) {
        rb.setBackground(BG_PANEL);
        rb.setForeground(TEXT_WHITE);
        rb.setFont(FONT_LABEL);
        rb.setFocusPainted(false);
    }

    private static void agregarFila(JPanel form, GridBagConstraints g, int row, String label, Component comp) {
        g.gridx = 0; g.gridy = row; g.weightx = 0.3;
        form.add(etiqueta(label), g);
        g.gridx = 1; g.weightx = 0.7;
        form.add(comp, g);
    }
}