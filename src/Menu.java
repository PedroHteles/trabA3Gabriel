import model.Cliente;
import service.ServiceImpl;
import utils.CustomScanner;

public class Menu {

    public static void main(String[] args) {
        String menu =
                "\n\n1. Listar clientes:\n"
                        + "2. Buscar cliente por email:\n"
                        + "3. criar cliente:\n"
                        + "4. remover cliente:\n"
                        + "5. editar nome cliente:\n"
                        + "0. Sair:\n\n";

        System.out.println(menu);
        ServiceImpl service = new ServiceImpl();
        CustomScanner sc = new CustomScanner();
        int opcao = sc.scInt("Digite uma opcao:");

        while (opcao != 0) {
            switch (opcao) {
                case 1 -> service.getClientes().forEach(Menu::soutClientes);
                case 2 -> service.serviceCliente().get().ifPresent(e -> service.getCliente(e.getId()).ifPresent(Menu::soutClientes));
                case 3 -> service.serviceCliente().create();
                case 4 -> service.serviceCliente().delete();
                case 5 -> service.serviceCliente().atualizarNomeCliente();

            }
            System.out.println(menu);
            opcao = sc.scInt("Digite uma opcao: ");
        }
    }

    private static void soutClientes(Cliente cliente) {
        System.out.println("Clientes cadastrados:" + " \n");
        System.out.println(
                "Id cliente: " + cliente.getId() + "\n" +
                        " nome: " + cliente.getNome() + "\n" +
                        " email: " + cliente.getEmail() + "\n"
        );
    }

}




