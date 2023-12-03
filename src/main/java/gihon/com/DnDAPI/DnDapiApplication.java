package gihon.com.DnDAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DnDapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DnDapiApplication.class, args);
	}

	//TODO
	//сделать над полями классов фильтры для вывода конкретной ошибки
	//добавить прибавление и вычитание уровней (выводить текущий в ответ и консоль)
	//добавить сохранение левелов магии
	//добавить сохранение навыков пользования предметами в персонажа
	//добавить сами предметы
	//использовать бинарный идентификатор для таблицы со всеми спелами
	//

}
