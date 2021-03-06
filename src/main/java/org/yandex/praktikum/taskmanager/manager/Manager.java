package org.yandex.praktikum.taskmanager.manager;

import org.yandex.praktikum.taskmanager.task.Epic;
import org.yandex.praktikum.taskmanager.task.Subtask;
import org.yandex.praktikum.taskmanager.task.Task;
import org.yandex.praktikum.taskmanager.task.TaskStatus;

import java.util.HashMap;
import java.util.Map;

public class Manager {
    private int idCount = 0;

    private HashMap <Integer, Task> taskMap = new HashMap<>();
    private HashMap <Integer, Epic> epicMap = new HashMap<>();
    private HashMap <Integer, Subtask> subtaskMap = new HashMap<>();

    public int getNewId(){
        return ++idCount;
    }

    public void addTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public void updateTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public void addEpic(Epic epic) {
        epicMap.put(epic.getId(), epic);
    }

    public void updateEpic(Epic epic){
        epicMap.put(epic.getId(), epic);
    }

    public void addSubtask(Subtask subtask) {
        subtaskMap.put(subtask.getId(), subtask);
    }

    /**
     * Обновляет подзадачу и проверяет на завершенность эпика, которому эта подзадача принадлежит
     */
    public void updateSubtask(Subtask subtask) {
        boolean changeStatus = true;
        for (Subtask subtaskInEpic : subtask.getEpicOwned().getSubtaskList()) {
            if (subtaskInEpic.getStatus() != TaskStatus.DONE) {
                changeStatus = false;
                break;
            }
        }

        if (changeStatus) {
            subtask.getEpicOwned().setStatus(TaskStatus.DONE);
        }
        else {
            subtask.getEpicOwned().setStatus(TaskStatus.IN_PROGRESS);
        }

        subtaskMap.put(subtask.getId(), subtask);
    }

    /**
     * Выводит список всех задач, подзадач и эпиков
     */
    public void getAllTasks() {
        System.out.println("Список всех задач:");
        for(Map.Entry<Integer, Task> entry : taskMap.entrySet()){
            System.out.println(entry.getValue());
        }
        System.out.println("Список всех эпиков:");
        for(Map.Entry<Integer, Epic> entry : epicMap.entrySet()){
            System.out.println(entry.getValue());
        }
        System.out.println("Список всех подазадач:");
        for(Map.Entry<Integer, Subtask> entry : subtaskMap.entrySet()){
            System.out.println(entry.getValue());
        }
    }

    /**
     * Очищает все списки задач, подзадач и эпиков
     */
    public void deleteAllTasks() {
        System.out.println("Удаление всех задач");
        taskMap.clear();
        subtaskMap.clear();
        epicMap.clear();
    }

    /**
     * Возвращает задачу по идентификатору и выводит ее в консоль
     * @param id идентификатор задач
     * @return объект задачи из сохраняемого списка задач
     */
    public Task getTaskById(int id) {
        Task result = taskMap.get(id);
        System.out.println(result);
        return result;
    }

    /**
     * Удаление задачи из списка всех задач
     * @param id идентификатор задачи
     */
    public void deleteTaskById(int id){
        System.out.println("Удалена задача:\n" + taskMap.get(id));
        taskMap.remove(id);
    }
}
