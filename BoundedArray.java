import java.util.*;

public class BoundedArray {

    public static int findIndex(List<Integer> a) {
        int l = 0, r = 1,ans = 1;

        while(a.get(r) != -1) {
            l = r;
            r *= 2;
        }
        int m;
        while (l <= r) {
            m= l + (r - l) / 2;
            if (a.get(m) == -1) {
                ans = m;
                r = m - 1;
                continue;
            } 
                l = m + 1;
            
        }

        return ans;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        List<Integer> a = new ArrayList<>();
        int num;
        //enter -2 for stop 
        while ((num = sc.nextInt()) != -2) {
            a.add(num);
        }

        int index = findIndex(a);

        System.out.println("Index x: " + index);
    }
}
