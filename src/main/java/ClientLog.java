import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientLog {
    private List<String[]> listLog = new ArrayList<>();

    //log(int productNum, int amount) покупатель добавил покупку, то это действие должно быть там сохранено
    public void log(int productNum, int amount) {
        String[] s = {String.valueOf(productNum), String.valueOf(amount)}; // преобразуем данные в массив стрингов
        listLog.add(s); // записываем в наш список
    }

    //exportAsCSV(File txtFile) для сохранения всего журнала действия в файл в формате csv
    public void exportAsCSV(File txtFile) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile))) {
            for (String[] s : listLog) {
                writer.writeNext(s); // пробегая по списку логов записываем в файл
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
