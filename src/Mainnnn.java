import java.util.Calendar;

class Mainnnn
    {
        // public static void main(String[] args)
        public Mainnnn()
            {
                Calendar now = Calendar.getInstance();
                System.out.println("Current date : " + (now.get(Calendar.MONTH) + 1) + "-"
                        + now.get(Calendar.DATE) + "-" + now.get(Calendar.YEAR));

                String[] strDays = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thusday",
                        "Friday", "Saturday"};
                // Day_OF_WEEK starts from 1 while array index starts from 0
                System.out.println("Current day is : " + strDays[now.get(Calendar.DAY_OF_WEEK) - 1]);
            }
    }