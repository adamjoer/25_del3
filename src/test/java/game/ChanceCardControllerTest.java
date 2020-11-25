package game;

import static org.junit.jupiter.api.Assertions.*;

class ChanceCardControllerTest {

    public static void main(String[] args){
        ChanceCardController controller = new ChanceCardController();

        controller.printCards();

        System.out.println();
        System.out.println();
        System.out.println("Single card: ------------------");
        System.out.println(controller.drawChanceCard().getChanceCardText());
        System.out.println(controller.drawChanceCard().getChanceCardText());
        System.out.println(controller.drawChanceCard().getChanceCardText());

        controller.shufflePile();

        System.out.println();
        System.out.println();
        System.out.println("After shuffle: ------------------");

        controller.printCards();

    }

}