public class Main {
    public static void main(String[] args) {
        Coffe kawa = new Coffe();
        System.out.println(kawa.serve());
        SugarDecorator sugarKawa = new SugarDecorator(kawa);
        System.out.println(sugarKawa.serve());
        MilkDecorator milkSugarCoffe=new MilkDecorator(sugarKawa);
        System.out.println(milkSugarCoffe.serve());

        Beverage tea = new Tea();
        tea= new SugarDecorator(tea);
        System.out.println(tea.serve());




    }
}