import java.util.*;

public class EquivalenceRelations {

    private static List<int[]> results = new ArrayList<>();

    /** 生成所有受限增长序列（RGS） */
    private static void backtrack(int[] a, int i, int n, int maxVal) {
        if (i == n) {
            results.add(a.clone());
            return;
        }
        for (int v = 0; v <= maxVal + 1; v++) {
            a[i] = v;
            backtrack(a, i + 1, n, Math.max(maxVal, v));
        }
    }

    public static List<int[]> generateRGS(int n) {
        results = new ArrayList<>();
        backtrack(new int[n], 0, n, -1);
        return results;
    }

    /** 将 RGS 转换为划分（块的列表） */
    public static List<List<Integer>> rgsToPartition(int[] rgs) {
        Map<Integer, List<Integer>> blocks = new LinkedHashMap<>();
        for (int i = 0; i < rgs.length; i++) {
            blocks.computeIfAbsent(rgs[i], k -> new ArrayList<>()).add(i + 1);
        }
        return new ArrayList<>(blocks.values());
    }

    /** 将划分转换为等价关系（有序对的排序列表） */
    public static List<int[]> partitionToRelation(List<List<Integer>> partition) {
        List<int[]> relation = new ArrayList<>();
        for (List<Integer> block : partition) {
            for (int i : block) {
                for (int j : block) {
                    relation.add(new int[]{i, j});
                }
            }
        }
        relation.sort((x, y) -> x[0] != y[0] ? x[0] - y[0] : x[1] - y[1]);
        return relation;
    }

    /** 打印所有等价关系及总数 */
    public static void printAllEquivalenceRelations(int n) {
        List<int[]> rgsList = generateRGS(n);
        int count = 0;
        for (int[] rgs : rgsList) {
            List<List<Integer>> partition = rgsToPartition(rgs);
            List<int[]> relation = partitionToRelation(partition);
            count++;
            StringBuilder sb = new StringBuilder("[");
            for (int k = 0; k < relation.size(); k++) {
                if (k > 0) sb.append(", ");
                sb.append("(").append(relation.get(k)[0]).append(", ").append(relation.get(k)[1]).append(")");
            }
            sb.append("]");
            System.out.println("等价关系" + count + ": " + sb);
        }
        System.out.println("\n总数: " + count);
    }

    public static void main(String[] args) {
        int n = args.length > 0 ? Integer.parseInt(args[0]) : 3;
        printAllEquivalenceRelations(n);
    }
}
