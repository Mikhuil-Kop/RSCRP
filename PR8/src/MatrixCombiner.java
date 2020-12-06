import java.util.ArrayList;

public class MatrixCombiner implements Result {

    private ArrayList<ArrayList<Integer>> firstMatrix = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> secondMatrix = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> combinedMatrix = new ArrayList<>();

    public MatrixCombiner(String rowFirstMatrix, String rowSecondMatrix){
        for(String matrixColumn : rowFirstMatrix.split(";")){
            ArrayList<Integer> matrixRow = new ArrayList<>();
            for(String matrixRowItem : matrixColumn.split(",")) {
                matrixRow.add(Integer.parseInt(matrixRowItem));
            }
            this.firstMatrix.add(matrixRow);
        }

        for(String matrixColumn : rowSecondMatrix.split(";")){
            ArrayList<Integer> matrixRow = new ArrayList<>();
            for(String matrixRowItem : matrixColumn.split(",")) {
                matrixRow.add(Integer.parseInt(matrixRowItem));
            }
            this.secondMatrix.add(matrixRow);
        }
    }

    public void combine(){
        for(int row = 0; row < firstMatrix.size(); row++){
            int columnIndex = 0;
            ArrayList<Integer> combinedRowArr = new ArrayList<>();
            for(int secondRow = 0; secondRow < secondMatrix.size(); secondRow++){
                int combinedValue = 0;
                for(int combinedRow = 0; combinedRow < firstMatrix.get(row).size(); combinedRow++){
                    combinedValue += firstMatrix.get(row).get(combinedRow) * secondMatrix.get(row).get(columnIndex);
                }
                combinedRowArr.add(combinedValue);
                columnIndex++;
            }
            this.combinedMatrix.add(combinedRowArr);
        }
    }

    private String arrToJSON(String arrName, ArrayList<ArrayList<Integer>> matrix, String position) {
        StringBuilder resultStr = new StringBuilder();
        resultStr.append("{");
        resultStr.append("\"matrixName\": \"").append(arrName).append("\",");
        resultStr.append("\"values\":[");
        for(ArrayList<Integer> matrixRow : matrix){
            resultStr.append("[");
            for(Integer matrixColumnItem : matrixRow){
                if(matrixColumnItem.equals(matrixRow.get(matrixRow.size()-1))){
                    resultStr.append(matrixColumnItem);
                }
                else{
                    resultStr.append(matrixColumnItem).append(",");
                }
            }
            if(matrixRow == matrix.get(matrix.size()-1)){
                resultStr.append("]");
            }
            else{
                resultStr.append("],");
            }
        }
        if(position.equals("notlast")){
            resultStr.append("]},");
        }
        else{
            resultStr.append("]}");
        }
        return resultStr.toString();
    }

    @Override
    public String getResult() {
        StringBuilder resultStr = new StringBuilder();
        resultStr.append("[");

        resultStr.append(arrToJSON("Первая матрица", this.firstMatrix, "notlast"));
        resultStr.append(arrToJSON("Вторая матрица", this.secondMatrix, "notlast"));
        resultStr.append(arrToJSON("Результат перемножения", this.combinedMatrix, "last"));

        resultStr.append("]");

        return resultStr.toString();
    }
}
