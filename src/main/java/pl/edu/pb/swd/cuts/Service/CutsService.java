package pl.edu.pb.swd.cuts.Service;

import org.springframework.stereotype.Service;
import pl.edu.pb.swd.cuts.Model.*;
import pl.edu.pb.swd.dataOperations.Service.ReadWriteService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CutsService {
    final ReadWriteService readWriteService;

    public CutsService(ReadWriteService readWriteService) {
        this.readWriteService = readWriteService;
    }

    public boolean checkIfTheSetIsTwoDimensional() {
        LinkedList<LinkedList<String>> data = readWriteService.readDataFromWorkingFile();
        int numberOfTwoDimensional = 3;
        return data.getFirst().size() == numberOfTwoDimensional;
    }

    public List<Row> listOfListToListOfRowObjects(LinkedList<LinkedList<String>> data) {
        List<Row> rows = new ArrayList<>();
        for (int i = 1; i < data.size(); i++) {
            double x1 = Double.parseDouble(data.get(i).get(0));
            double x2 = Double.parseDouble(data.get(i).get(1));
            String classifier = data.get(i).get(2);
            rows.add(new Row(x1, x2, classifier));
        }
        return rows;
    }

    public CutResultForTwoDimensionalPlane createCuts() {
        LinkedList<LinkedList<String>> data = readWriteService.readDataFromWorkingFile();
        List<Row> rows = listOfListToListOfRowObjects(data);

        List<Row> rowsSortXTemp = rows.stream()
                .sorted(Comparator.comparing(Row::getX))
                .collect(Collectors.toList());

        List<Row> rowsSortYTemp = rows.stream()
                .sorted(Comparator.comparing(Row::getY))
                .collect(Collectors.toList());

        LinkedList<Row> rowsSortByX = new LinkedList<>(rowsSortXTemp);
        LinkedList<Row> rowsSortByY = new LinkedList<>(rowsSortYTemp);
        Map<Integer, Integer> countByClass = new LinkedHashMap<>();
        int numberOfCuts = 0;
        int numberOfObjectsRemoved = 0;
        double minX = rowsSortByX.getFirst().getX();
        double minY = rowsSortByY.getFirst().getY();
        double maxX = rowsSortByX.getLast().getX();
        double maxY = rowsSortByY.getLast().getY();
        LinkedList<Line> lines = new LinkedList<>();
        LinkedList<HyperPlane> hyperPlanes = new LinkedList<>();
        do {
            countByClass.put(0, 0);
            countByClass.put(1, 0);
            countByClass.put(2, 0);
            countByClass.put(3, 0);
//            ArrayList<Double> minmaxXY = new ArrayList<>(
//                    Arrays.asList(rowsSortByX.getFirst().getX(),
//                            rowsSortByY.getFirst().getY(),
//                            rowsSortByX.getLast().getX(),
//                            rowsSortByY.getLast().getY())
//            );
//        ArrayList<String> classifierList = new ArrayList<>(
//                Arrays.asList(rowsSortByX.getFirst().getClassifier(), rowsSortByY.getFirst().getClassifier(),
//                        rowsSortByY.getFirst().getClassifier(),
//                        rowsSortByX.getLast().getClassifier(),
//                        rowsSortByY.getLast().getClassifier())
//        );

            LinkedList<LinkedList<Row>> rowsList = new LinkedList<>();
            rowsList.add(new LinkedList<>());
            rowsList.add(new LinkedList<>());
            rowsList.add(new LinkedList<>());
            rowsList.add(new LinkedList<>());

            for (int i = 0; i < 2; i++) {
                LinkedList<Row> rowsListByIndex = new LinkedList<>();
                int count = 0;
                if (i == 0) {
                    String classifier = rowsSortByX.getFirst().getClassifier();
                    for (int j = 0; j < rowsSortByX.size(); j++) {
                        if (!classifier.equals(rowsSortByX.get(j).getClassifier())) {
                            break;
                        }
                        count++;
                        rowsListByIndex.add(rowsSortByX.get(j));
                    }
                    rowsList.set(i, rowsListByIndex);
                } else {
                    String classifier = rowsSortByY.getFirst().getClassifier();
                    for (int j = 0; j < rowsSortByY.size(); j++) {
                        if (!classifier.equals(rowsSortByY.get(j).getClassifier())) {
                            break;
                        }
                        count++;
                        rowsListByIndex.add(rowsSortByY.get(j));
                    }
                    rowsList.set(i, rowsListByIndex);
                }
                if (i == 0) {
                    countByClass.put(0, count);
                } else {
                    countByClass.put(1, count);
                }
            }
            for (int i = 2; i < 4; i++) {
                LinkedList<Row> rowsListByIndex = new LinkedList<>();
                int count = 0;
                if (i == 2) {
                    int size = rowsSortByX.size();
                    String classifier = rowsSortByX.getLast().getClassifier();
                    for (int j = size - 1; j >= 0; j--) {
                        if (!classifier.equals(rowsSortByX.get(j).getClassifier())) {
                            break;
                        }
                        count++;
                        rowsListByIndex.add(rowsSortByX.get(j));
                    }
                    rowsList.set(i, rowsListByIndex);
                } else {
                    int size = rowsSortByY.size();
                    String classifier = rowsSortByY.getLast().getClassifier();
                    for (int j = size - 1; j >= 0; j--) {
                        if (!classifier.equals(rowsSortByY.get(j).getClassifier())) {
                            break;
                        }
                        count++;
                        rowsListByIndex.add(rowsSortByY.get(j));
                    }
                    rowsList.set(i, rowsListByIndex);
                }
                if (i == 2) {
                    countByClass.put(2, count);
                } else {
                    countByClass.put(3, count);
                }
            }

            Map<Integer, Integer> result = countByClass.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            Integer key = 0;
            for (Map.Entry<Integer, Integer> entry : result.entrySet()) {
                key = entry.getKey();
            }
            Line line = new Line();
            if (key == 0) {
                line = new Line(new Point(rowsList.get(0).getLast().getX(), minY), new Point(rowsList.get(0).getLast().getX(), maxY));
                line.setDirection("minX");
            } else if (key == 1) {
                line = new Line(new Point(minX, rowsList.get(1).getLast().getY()), new Point(maxX, rowsList.get(1).getLast().getY()));
                line.setDirection("minY");

            } else if (key == 2) {
                line = new Line(new Point(rowsList.get(2).getFirst().getX(), minY), new Point(rowsList.get(2).getFirst().getX(), maxY));
                line.setDirection("maxX");

            } else if (key == 3) {
                line = new Line(new Point(minX, rowsList.get(3).getFirst().getY()), new Point(maxX, rowsList.get(3).getFirst().getY()));
                line.setDirection("maxY");

            }
            lines.add(line);

            rowsSortByX.removeAll(rowsList.get(key));
            rowsSortByY.removeAll(rowsList.get(key));
            hyperPlanes.add(new HyperPlane(rowsList.get(key)));
            numberOfCuts++;
        } while (rowsSortByX.size() > 0);
        System.out.println(hyperPlanes);

        for (HyperPlane plane : hyperPlanes) {
            List<Integer> vector = new ArrayList<>();
            double x = plane.getRows().get(0).getX();
            double y = plane.getRows().get(0).getY();
            for (Line line : lines) {
                if (line.getDirection().equals("minX") && x <= line.getPointA().getX()) {
                    vector.add(1);
                } else if (line.getDirection().equals("minX") && x >= line.getPointA().getX()) {
                    vector.add(0);
                } else if (line.getDirection().equals("maxX") && x >= line.getPointA().getX()) {
                    vector.add(1);
                } else if (line.getDirection().equals("maxX") && x <= line.getPointA().getX()) {
                    vector.add(1);
                } else if (line.getDirection().equals("minY") && y <= line.getPointA().getY()) {
                    vector.add(1);
                } else if (line.getDirection().equals("minY") && y >= line.getPointA().getY()) {
                    vector.add(0);
                } else if (line.getDirection().equals("maxY") && y >= line.getPointA().getY()) {
                    vector.add(1);
                } else if (line.getDirection().equals("maxY") && y <= line.getPointA().getY()) {
                    vector.add(0);
                }
            }
            plane.setVector(vector);
        }
        CutResultForTwoDimensionalPlane cutResultForTwoDimensionalPlane = new CutResultForTwoDimensionalPlane();
        cutResultForTwoDimensionalPlane.setHyperPlanes(hyperPlanes);
        cutResultForTwoDimensionalPlane.setLines(lines);
        cutResultForTwoDimensionalPlane.setNumberOfCuts(numberOfCuts);
        cutResultForTwoDimensionalPlane.setNumberOfObjectsRemoved(numberOfObjectsRemoved);
        System.out.print(hyperPlanes);
        return cutResultForTwoDimensionalPlane;
    }
}
