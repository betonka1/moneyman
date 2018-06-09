package com.mike.moneyman.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mike.moneyman.domain.Pipeline;
import com.mike.moneyman.domain.Task;
import com.mike.moneyman.domain.Transition;
import com.mike.moneyman.service.PipelineService;
import com.mike.moneyman.service.TaskService;
import com.mike.moneyman.service.TransitionService;
import com.mike.moneyman.utils.YamlUtils;
import com.mike.moneyman.yaml.YamlBean;
import com.mike.moneyman.yaml.YamlTransitionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class PipelineController {

    private static final String[] STATUS = {"COMPLETED", "SKIPPED","FAILED"};

    private final PipelineService pipelineService;

    private final TaskService taskService;

    private final TransitionService transitionService;

    @Autowired
    public PipelineController(PipelineService pipelineService, TaskService taskService, TransitionService transitionService) {
        this.pipelineService = pipelineService;
        this.taskService = taskService;
        this.transitionService = transitionService;
    }

    @RequestMapping("/pipe/{id}")
    @ResponseBody
    public Pipeline showPipeline(@PathVariable("id") Long id) throws IOException {
        YamlUtils.write(pipelineService.getById(id));
        return pipelineService.getById(id);
    }


    @RequestMapping(value = "/execute/stop/{pipeline}", method = RequestMethod.POST)
    @ResponseBody
    public String stopPipeline(@PathVariable("pipeline") String pipeline) {
        Pipeline pipeline1 = pipelineService.getByName(pipeline);
        pipeline1.setStatus(STATUS[2]);
        pipelineService.update(pipeline1);
        return pipeline1.getStatus();
    }

    @RequestMapping(value = "/execute/{pipeline}", method = RequestMethod.POST)
    @ResponseBody
    public String executePipeline(@PathVariable("pipeline") String pipeline) {
        Thread mainThread = new Thread(() -> {
            Pipeline pipelineForExecute = pipelineService.getByName(pipeline);
            pipelineForExecute.setStatus("IN PROGRESS");
            pipelineService.update(pipelineForExecute);
            List<Task> tasks = pipelineForExecute.getTasks();

            pipelineForExecute.setStartTime(LocalDateTime.now().toString());
            for (Task task : tasks) {

                Thread thread = new Thread(() -> {
                    switch (task.getAction()) {
                        case "print":
                            task.setStatus("IN PROGRESS");
                            task.setStartTime(LocalDateTime.now().toString());
                            taskService.update(task);
                            System.out.println(task.toString());
                            task.setStatus(STATUS[0]);
                            task.setEndTime(LocalDateTime.now().toString());
                            taskService.update(task);
                            break;
                        case "random":
                            task.setStartTime(LocalDateTime.now().toString());
                            System.out.println((task.toString()));
                            task.setStatus(getRandomStatus());
                            task.setEndTime(LocalDateTime.now().toString());
                            taskService.update(task);
                            if (task.getStatus().equals(STATUS[2])) {
                                pipelineForExecute.setStatus(STATUS[2]);
                                pipelineForExecute.setEndTime(LocalDateTime.now().toString());
                                pipelineService.update(pipelineForExecute);

                            }
                            break;
                        case "completed":
                            task.setStartTime(LocalDateTime.now().toString());
                            System.out.println((task.toString()));
                            task.setStatus(STATUS[0]);
                            task.setEndTime(LocalDateTime.now().toString());
                            taskService.update(task);
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            break;
                        case "delayed":
                            task.setStartTime(LocalDateTime.now().toString());
                            System.out.println((task.toString()));
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            task.setStatus(STATUS[0]);
                            task.setEndTime(LocalDateTime.now().toString());
                            taskService.update(task);
                            break;
                        default:
                            break;
                    }
                });
                if (!pipelineService.getByName(pipeline).getStatus().equals(STATUS[2])) {
                    if (task.getStatus().equals(STATUS[2])) {
                        break;
                    } else if (!task.getStatus().equals(STATUS[0]) || !task.getStatus().equals(STATUS[1])) {
                        List<Transition> transitions = transitionService.findAllByTransition(task.getName());
                        for (Transition t : transitions) {
                            if (t.getTask().getStatus().equals(STATUS[0]) || t.getTask().getStatus().equals(STATUS[1])) {
                                thread.run();
                            } else if (t.getTask().getStatus().equals(STATUS[2])) {
                                break;
                            } else {
                                try {
                                    thread.join();
                                    thread.run();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (transitions.size() == 0) {
                            thread.run();
                        }

                    }
                } else {
                    pipelineForExecute.setEndTime(LocalDateTime.now().toString());
                    pipelineService.update(pipelineForExecute);
                    break;
                }

            }
            pipelineForExecute.setEndTime(LocalDateTime.now().toString());
            pipelineService.update(pipelineForExecute);
        });
        mainThread.run();
        return pipelineService.getByName(pipeline).getStatus();
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public List<Pipeline> savePipeline(@RequestParam("file") MultipartFile file) {
        YamlBean parsedYaml = null;
        try {
            parsedYaml = YamlUtils.parse(convert(file));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Pipeline pipeline = new Pipeline();

        if (parsedYaml != null) {
            pipeline.setName(parsedYaml.getName());
            pipeline.setDescription(parsedYaml.getDescription());

            List<Task> tasks = getTasksFromHashMap(parsedYaml);

            for (Task t : tasks) {
                t.setPipeline(pipeline);
            }

            List<YamlTransitionBean> transitionsYaml = getTransitionsFromHashMap(parsedYaml);

            for (int i = 0; i < transitionsYaml.size(); i++) {
                List<Transition> transitions = new ArrayList<>();

                int id = findTaskByName(tasks, transitionsYaml.get(i).getTask());// task id to set transition


                if (id != -1) {
                    List<YamlTransitionBean> transitionBeans
                            = findTransitions(transitionsYaml, tasks.get(id).getName(), transitionsYaml.get(i).getTransition());// find all transition to task

                    for (YamlTransitionBean bean : transitionBeans) {
                        Transition newTransition = new Transition();
                        newTransition.setTransition(bean.getTransition());
                        newTransition.setTask(tasks.get(id));
                        transitions.add(newTransition);
                    }


                    tasks.get(id).setTransition(transitions);
                    transitionService.save(transitions);
                }
            }
            Task task = findTaskWithoutTransition(tasks);
            if (task != null) {
                List<Transition> transitions = new ArrayList<>();
                Transition newTransition = new Transition();
                newTransition.setTransition("NONE");
                newTransition.setTask(task);
                transitions.add(newTransition);
                transitionService.save(transitions);
            }
        }
        return pipelineService.getAll();
    }

    @RequestMapping("/send-file")
    public String sendFile() {
        return "sendFile";
    }

    private File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }


    private int findTaskByName(List<Task> tasks, String name) {

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    private List<YamlTransitionBean> findTransitions(List<YamlTransitionBean> transitionBeans, String name, String trans) {
        List<YamlTransitionBean> newTransitions = new ArrayList<>();
        for (YamlTransitionBean bean : transitionBeans) {
            if (bean.getTask().equals(name) && bean.getTransition().equals(trans)) {
                newTransitions.add(bean);
                return newTransitions;
            }
        }
        return null;
    }

    private List<Task> getTasksFromHashMap(YamlBean bean) {
        ObjectMapper mapper = new ObjectMapper();
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < bean.getTasks().size(); i++) {
            Task task = mapper.convertValue(bean.getTasks().get(i), Task.class);
            tasks.add(task);
        }
        return tasks;
    }

    private List<YamlTransitionBean> getTransitionsFromHashMap(YamlBean bean) {
        ObjectMapper mapper = new ObjectMapper();
        List<YamlTransitionBean> transitionBeans = new ArrayList<>();
        for (int i = 0; i < bean.getTransitions().size(); i++) {
            YamlTransitionBean yamlTransitionBean = mapper.convertValue(bean.getTransitions().get(i), YamlTransitionBean.class);
            transitionBeans.add(yamlTransitionBean);
        }
        return transitionBeans;
    }

    private String getRandomStatus() {
        int index = new Random().nextInt(STATUS.length);
        return STATUS[index];

    }

    private Task findTaskWithoutTransition(List<Task> tasks) {
        for (Task t : tasks) {
            if (t.getTransition() == null) {
                return t;
            }
        }
        return null;
    }


}
