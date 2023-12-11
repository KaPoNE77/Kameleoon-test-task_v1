package com.example.Kameleoontesttask_v1.controller;

import com.example.Kameleoontesttask_v1.model.Client;
import com.example.Kameleoontesttask_v1.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    public class ClientController {
    /*
     * @RestController — говорит спрингу, что данный класс является REST контроллером. Т.е. в данном классе будет реализована логика обработки клиентских запросов
     */

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    /*
     * @Autowired — говорит спрингу, что в этом месте необходимо внедрить зависимость. В конструктор мы передаем интерфейс
     * ClientService. Реализацию данного сервиса мы пометили аннотацией @Service ранее, и теперь спринг сможет передать
     * экземпляр этой реализации в конструктор контроллера.
     */
    @PostMapping(value = "/clients")                               // данный метод обрабатывает POST запросы на адрес /clients
    public ResponseEntity<?> create(@RequestBody Client client) {  // этот возвращает ResponseEntity<?>. ResponseEntity — специальный класс для возврата ответов. С помощью него мы сможем в дальнейшем вернуть клиенту HTTP статус код.
        //Метод принимает параметр @RequestBody Client client, значение этого параметра подставляется из тела запроса. Об этом говорит аннотация  @RequestBody.
        clientService.create(client);
        return new ResponseEntity<>(HttpStatus.CREATED);          // После чего возвращаем статус 201 Created, создав новый объект ResponseEntity и передав в него нужное значение енума HttpStatus.
    }

    /*
     * Далее реализуем операцию Read:
     */
    @GetMapping(value = "/clients")                               // обрабатываем GET запросы.
    public ResponseEntity<List<Client>> read() {                  // возвращаем ResponseEntity<List<Client>>, только в этот раз, помимо HTTP статуса, мы вернем еще и тело ответа, которым будет список клиентов.
        final List<Client> clients = clientService.readAll();     // В REST контроллерах спринга все POJO объекты, а также коллекции POJO объектов, которые возвращаются в качестве тел ответов, автоматически сериализуются
        // в JSON, если явно не указано иное. Нас это вполне устраивает.

        return clients != null && !clients.isEmpty()
                ? new ResponseEntity<>(clients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        /*
         * Внутри метода, с помощью нашего сервиса мы получаем список всех клиентов. Далее, в случае если список не null и не пуст, мы возвращаем c помощью класса ResponseEntity сам список клиентов и
         * HTTP статус 200 OK. Иначе мы возвращаем просто HTTP статус 404 Not Found.
         */
    }

        /*
         * Далее реализуем возможность получать клиента по его id:
         */
        @GetMapping(value = "/clients/{id}")                                              // Из нового, у нас тут появилась переменная пути. Переменная, которая определена в URI. value = "/clients/{id}". Мы указали ее в фигурных скобках. А в параметрах метода принимаем её в качестве int переменной, с помощью аннотации @PathVariable(name = "id").
        public ResponseEntity<Client> read(@PathVariable(name = "id") int id) {
            final Client client = clientService.read(id);
            /*
             * Данный метод будет принимать запросы на uri вида /clients/{id}, где вместо {id} может быть любое численное значение. Данное значение, впоследствии, передается переменной int id — параметру метода.
             */

            return client != null
                    ? new ResponseEntity<>(client, HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @PutMapping(value = "/clients/{id}")
        public ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody Client client) {
            final boolean updated = clientService.update(client, id);

            return  updated
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        @DeleteMapping(value = "/clients/{id}")
        public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
            final boolean deleted = clientService.delete(id);

            return deleted
                    ? new ResponseEntity<>(HttpStatus.OK)
                    : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

            /*
             * Чего-то существенно нового в данных методах нет, поэтому подробное описание пропустим. Единственное, о чем стоит сказать:
             * метод update обрабатывает PUT запросы (аннотация @PutMapping), а метод delete обрабатывает DELETE запросы (аннотация DeleteMapping).
             */
        }


}


