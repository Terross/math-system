//package com.mathsystem.domain.plugin;
//
//import com.mathsystem.domain.plugin.repository.PluginAnswerProjection;
//import com.mathsystem.domain.plugin.repository.PluginProjection;
//import com.mathsystem.domain.graph.GraphVerifier;
//import com.mathsystem.domain.graph.repository.EdgeProjection;
//import com.mathsystem.domain.graph.repository.GraphProjection;
//import com.mathsystem.domain.graph.repository.VertexProjection;
//import com.mathsystem.domain.task.repository.TaskProjection;
//import com.mathsystem.lib.graphapi.AbstractGraph;
//import com.mathsystem.lib.graphapi.GraphFactory;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.crossstore.ChangeSetPersister;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class Validator {
//
//    private final GraphVerifier graphVerifier;
//
//    public boolean verifyTask(TaskProjection taskProjection, GraphProjection graphProjection) throws ChangeSetPersister.NotFoundException {
////        boolean result = true;
////
////        AbstractGraph abstractGraph = GraphFactory
////                .createGraph(
////                        graphProjection.getVertexCount(),
////                        graphProjection.getEdgeCount(),
////                        findEdgeList(graphProjection),
////                        graphProjection.getGraphType(),
////                        graphProjection.getVertexProjections()
////                );
////
////        for (PluginAnswerProjection projection: taskProjection.getPluginAnswers()) {
////
////            int index = projection.getPluginProjection().getFileName().lastIndexOf('.');
////            String name  = projection.getPluginProjection().getFileName().substring(0, index);
////
////            switch (projection.getPluginType()) {
////                case PROPERTY -> {
////                    GraphProperty graphProperty = (GraphProperty) PluginFactory.loadPlugin(name);
////                    result = graphProperty.execute(abstractGraph) == Boolean.parseBoolean(projection.getAnswer());
////                }
////                case CHARACTERISTIC -> {
////                    GraphCharacteristic graphCharacteristic = (GraphCharacteristic) PluginFactory.loadPlugin(name);
////                    if (graphCharacteristic.execute(abstractGraph).equals(Integer.parseInt(projection.getAnswer()))) {
////                        result = false;
////                    }
////                }
////            }
////        }
////        return result;
//        return false;
//    }
//
//    public List<PlgAns> findAnswersForPlugins(List<PluginProjection> plugins, GraphProjection graphProjection) throws ChangeSetPersister.NotFoundException {
//        List<Validator.PlgAns> plgAnsList = new ArrayList<>();
//        List<EdgeProjection> edgeProjectionArrayList = findEdgeList(graphProjection);
//
//        AbstractGraph abstractGraph = GraphFactory.createGraph(graphProjection.getVertexCount(),
//                graphProjection.getEdgeCount(),
//                edgeProjectionArrayList,
//                graphProjection.getGraphType(),
//                graphProjection.getVertexProjections());
//
//        for (PluginProjection plugin : plugins) {
//            plgAnsList.add(
//                            new PlgAns(
//                                    plugin.getName(),
//                                    graphVerifier.findForGraph(abstractGraph, plugin))
//            );
//        }
//
//        return plgAnsList;
//    }
//
//    public static class PlgAns {
//        private String pluginName;
//        private String answer;
//
//        public PlgAns(String pluginName, String answer) {
//            this.pluginName = pluginName;
//            this.answer = answer;
//        }
//
//        public String getPluginName() {
//            return pluginName;
//        }
//        public void setPluginName(String pluginName) {
//            this.pluginName = pluginName;
//        }
//        public String getAnswer() {
//            return answer;
//        }
//        public void setAnswer(String answer) {
//            this.answer = answer;
//        }
//    }
//
//    private List<EdgeProjection> findEdgeList(GraphProjection graphProjection) throws ChangeSetPersister.NotFoundException {
//        List<VertexProjection> vertexProjections = graphProjection.getVertexProjections();
//        ArrayList<EdgeProjection> edgeProjectionArrayList = new ArrayList<>();
//        for (VertexProjection vertexProjection :
//                graphProjection.getVertexProjections()) {
//            for (EdgeProjection edgeProjection :
//                    vertexProjection.getIncomingEdgeProjections()) {
//                edgeProjection.setToVertexProjection(vertexProjection);
//                edgeProjection.setFromVertexProjection(vertexProjections
//                        .stream()
//                        .filter(v -> v.getName().equals(edgeProjection.getFromV()))
//                        .findFirst()
//                        .orElseThrow(ChangeSetPersister.NotFoundException::new));
//                edgeProjectionArrayList.add(edgeProjection);
//            }
//            vertexProjection.setGraphProjection(graphProjection);
//        }
//
//        return edgeProjectionArrayList;
//    }
//}
