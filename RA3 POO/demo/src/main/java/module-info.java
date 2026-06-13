module com.seu.projeto { // O nome do módulo deve bater com o do seu pom.xml ou package
    // Dependências necessárias
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    // Permite que o JavaFX use reflexão para instanciar suas classes
    // O 'to javafx.fxml' limita esse acesso apenas ao módulo do JavaFX, aumentando a segurança
    opens com.capitulozero.view to javafx.fxml;

    // (Opcional) Exporta se outras classes precisarem acessar publicamente
    exports com.capitulozero.view;
    exports com.capitulozero;
    opens com.capitulozero to javafx.fxml;
}