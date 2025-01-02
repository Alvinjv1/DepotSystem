//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Parcel p1 = new Parcel("C101",3,4.5,5,1,1);
       System.out.println(p1.toString());
       p1.setStatus(true);
        System.out.println(p1.toString());
    }
}