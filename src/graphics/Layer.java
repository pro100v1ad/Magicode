package graphics;
/*
Принимает строку "000_000"
и преобразовывает ее в два числа
где 0 - означает ничего не рисовать
1 и далее - означает определенную текстуру.
 */
public class Layer {
    private int firstLayer;
    private int secondLayer;

    public void setLayers(String input) {
        // Разделяем строку по символу '_'
        String[] parts = input.split("_");

        // Проверяем, что строка разделилась на две части
        if (parts.length == 2) {
            // Преобразуем первую часть в число
            firstLayer = Integer.parseInt(parts[0]);

            // Преобразуем вторую часть в число
            secondLayer = Integer.parseInt(parts[1]);

        } else {
            System.out.println("Строка не соответствует формату 'число_число'");
        }
    }

    public int getLayer(int number) {
        if(number == 1) return firstLayer;
        else return secondLayer;
    }

}
