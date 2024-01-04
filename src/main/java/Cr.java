/**
 * @Author lihuanjia
 * @Date 2024/1/4、22:22
 **/


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class Cr {
}



class test014_03 {
    public static void main(String[] args) {
        System.out.println("欢迎来到学生管理系统");
        System.out.println("请选择操作1登录 2注册 3忘记密码");
        ArrayList<User>list=new ArrayList<>();

        while (true) {
            Scanner sc = new Scanner(System.in);
            int choose = sc.nextInt();
            switch (choose) {
                case 1:
                    login(list);
                    break;
                case 2:
                    register(list);
                    break;
                case 3:
                    forgetPassword(list);
                    break;
                default:
                    System.out.println("没有这个选择");
            }
        }
    }

    public static void login(ArrayList<User>list) {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入用户名");
        String usename=sc.next();
        //判断用户名是否存在
        boolean flag= contain(list,usename);
        if(!flag){
            System.out.println("用户名"+usename+"未注册，请先注册再登录");
            return;
        }
        System.out.println("请输入密码");
        String password=sc.next();

        while (true) {
            String rightcode=getCode();
            System.out.println("当前验证码为"+rightcode);
            System.out.println("请输入验证码");
            String code=sc.next();
            if(code.equalsIgnoreCase(rightcode)){
                System.out.println("验证码正确");
                break;
            }else{
                System.out.println("验证码错误");
            }
        }
        //验证用户名和密码是否正确
        //集合中是否包含用户名和密码
        //定义一个方法进行判断
        //封装思想的应用：
        //我们可以把一些零散的数据，封装成一个对象
        //以后传递参数的时候，传递一个整体就行，不需要这些零散的数据
        User userInfo=new User(usename,password,null,null);
        boolean result=cherkUserInfo(list,userInfo);
        if(result){
            System.out.println("登录成功，开始使用学生管理系统");
        }else{
            System.out.println("登录失败，用户名或者密码错误");
        }


    }

    private static boolean cherkUserInfo(ArrayList<User>list,User userInfo) {
        //遍历集合，判断用户是否存在
        for (int i = 0; i < list.size(); i++) {
            User user=list.get(i);
            if(user.getUserName().equals(userInfo.getUserName())&&user.getPassword().equals(userInfo.getPassword())){
                return true;
            }
        }
        return false;
    }

    public static void forgetPassword(ArrayList<User>list) {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入用户名");
        String username=sc.next();
        boolean flag=contain(list,username);
        if(!flag){
            System.out.println("当前用户未注册，请先注册");
            return;
        }
        //键盘录入
        System.out.println("请输入身份证号码");
        String personID=sc.next();
        System.out.println("请输入手机号码");
        String phonenumber=sc.next();

        //比较用户对象中的手机号码和身份证号码是否相同
        //需要把单独的用户对象的索引先获取
        int index=findindex(list,username);
        User user=list.get(index);
        //判断用户对象中的手机号码和身份证号码是否相同
        if(!(user.getIdNumber().equalsIgnoreCase(personID)&&user.getPhoneNumber().equals(phonenumber))){
            System.out.println("身份证号码或者手机号码输入有误，不能修改密码");
            return;
        }
        //当代码执行到这里，表示所有的数据都验证成功，直接修改即可。
        String newPassword;
        while (true) {
            System.out.println("请输入新的密码");
            newPassword=sc.next();
            System.out.println("请再次输入新的密码");
            String againPassword=sc.next();
            if(newPassword.equals(againPassword)){
                System.out.println("两次密码输入一致");
                break;
            }else{
                System.out.println("两次密码输入不一致，需要重新输入");
            }
        }

        user.setPassword(newPassword);
        System.out.println("密码修改成功");


    }

    private static int findindex(ArrayList<User> list, String username) {
        for (int i = 0; i < list.size(); i++) {
            User user=list.get(i);
            if(user.getUserName().equals(username)){
                return i;
            }
        }
        return -1;
    }

    public static void register(ArrayList<User>list) {
        Scanner sc=new Scanner(System.in);
        String userName;
        String password;
        String personID;
        String phoneName;
        while (true) {
            System.out.println("请输入注册名称");
            userName=sc.next();
            //要求：用户名唯一，用户名长度为3~15之间，只能是字母加数字，但不能是纯数字
            // 开发细节：先判断格式是否符合要求，再验证是否唯一
            //因为在以后所有的数据中，都是存在数据库中，如果我们要校验，需要使用到网络资源
            boolean flag1=checkUserName(userName);
            if (!flag1){
                System.out.println("格式不满足条件，请重新输入用户名");
                continue;
            }
            //校验用户名是否唯一
            //username到集合中判断是否唯一
            boolean flag2=contain(list,userName);
            if(flag2){
                //表示用户名已存在，我们需要不存在
                System.out.println("用户名"+userName+"已存在");
            }else{
                System.out.println("用户名"+userName+"可用");
                break;
            }
        }
        //键盘录入密码
        while (true) {
            System.out.println("请输入要注册的密码");
            password= sc.next();
            System.out.println("请再次输入密码");
            String againPassword= sc.next();
            if(!password.equals(againPassword)){
                System.out.println("密码不一致，请重新输入");
                continue;
            }else {
                System.out.println("密码一致，请输入其他信息");
                break;
            }
        }
        //键盘录入身份证号码
        while (true) {
            System.out.println("请输入身份证号码");
            personID= sc.next();
            boolean flag=checkpersonID(personID);
            if(flag){
                System.out.println("身份证号码满足要求");
                break;
            }else{
                System.out.println("身份证号码不满足要求，请重新输入");
            }
        }
        //键盘录入手机号码
        while (true) {
            System.out.println("请输入手机号码");
            phoneName=sc.next();
            boolean flag= checkPhoneName(phoneName);
            if (flag){
                System.out.println("手机号码格式正确");
                break;
            }else{
                System.out.println("手机号码格式错误，请重新输入");
                continue;
            }
        }

        //需要把用户名、密码、身份证号码、手机号码都添加到用户对象中
        User u=new User(userName, password,personID,phoneName);
        //把用户对象添加到集合中
        list.add(u);
        System.out.println("注册成功");
        //遍历集合
        printlist(list);
    }

    private static void printlist(ArrayList<User> list) {
        for (int i = 0; i < list.size(); i++) {
            User user=list.get(i);
            System.out.println(user.getUserName()+","+user.getPassword()+","+user.getIdNumber()+","+user.getPhoneNumber());
        }
    }

    private static boolean checkPhoneName(String phoneName) {
        if(phoneName.length()!=11){
            return  false;
        }
        boolean flag=phoneName.startsWith("0");
        if(flag){
            return  false;
        }
        for (int i = 0; i < phoneName.length(); i++) {
            char c= phoneName.charAt(i);
            if(!(c>='0' &&c<='9')){
                return  false;
            }
        }
        //当循环结束后，表示每个字符都在0-9之间
        return true;
    }

    private static boolean checkpersonID(String personID) {
        //长度18
        if(personID.length()!=18){
            return false;
        }
        //不能以0开头
        boolean falg=personID.startsWith("0");
        if(falg){
            //如果以0开头，返回false
            return false;
        }
        //前17位必须是数字
        for (int i = 0; i < personID.length()-1; i++) {
            char c=personID.charAt(i);
            if(!(c>='0'&&c<='9')){
                return  false;
            }
        }
        //最后一位可以是数字也可以是大写或者小写的z
        char endChar=personID.charAt(personID.length()-1);
        if((endChar>='1'&&endChar<='9')||(endChar=='z')||(endChar=='Z')){
            return  true;
        }else {
            return  false;
        }

    }

    private static boolean contain(ArrayList<User> list, String userName) {
        for (int i = 0; i < list.size(); i++) {
            User user=list.get(i);
            String rightUserName= user.getUserName();
            if(rightUserName.equals(userName)){
                return  true;
            }
        }
        return  false;
    }

    private static boolean checkUserName(String userName) {
        //要求：用户名长度为3~15之间。
        int len=userName.length();
        if(len<3||len>15){
            return false;
        }
        //当代码执行到这里，表示用户名的长度是符合要求的。
        //继续校验：只能是字母加数字，但不能是纯数字。
        //需要循环得到username的全部字符，如果有一个字符不是数字或者字母，则返回false
        for (int i = 0; i < userName.length(); i++) {
            char c=userName.charAt(i);
            if(!(('a'<=c&&c<='z')||('A'<=c&&c<='Z')||('0'<=c&&c<='9'))){
                return false;
            }
        }
        //当代码执行到这里，表示用户名满足长度的要求，满足内容的要求
        //继续校验：不能是纯数字
        //统计用户名有多少个字母就满足要求
        int count=0;
        for (int i = 0; i < userName.length(); i++) {
            char c=userName.charAt(i);
            if(('a'<=c&&c<='z')||('A'<=c&&c<='Z')){
                count++;
                break;
            }
        }
        return count>0;

    }

    //生成一个验证码
    private static String getCode(){
        //1.创造一个集合添加所有的大写和小写
        ArrayList<Character>list=new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char)('a'+i));
            list.add((char)('A'+i));
        }
        //2.要随机抽取4个字符
        StringBuilder sb=new StringBuilder();
        Random r=new Random();
        for (int i = 0; i < 4; i++) {
            //获取随机索引
            int index =r.nextInt(list.size());
            //利用随机索引获取随机字符
            char c=list.get(index);
            //拼接字符,把随机字符添加到sb
            sb.append(c);
        }
        //把随机数字添加到末尾
        int number=r.nextInt(10);
        sb.append(number);
        //如果我们要修改字符串的内容
        //先把字符串变成字符数组，在数组中修改，然后再创建一个新的字符串
        char[]arr= sb.toString().toCharArray();
        int randomIndex=r.nextInt(arr.length);
        //最大索引指向的元素，跟随机索引指向的元素进行位置交换
        char temp=arr[randomIndex];
        arr[randomIndex]=arr[arr.length-1];
        arr[arr.length-1]=temp;
        return new String(arr);
    }
}



class test014_02 {
    public static void main(String[] args) {
        ArrayList<Student> list = new ArrayList<>();
        loop:
        while (true) {
            //1.打印初始界面的提示信息
            System.out.println("-----------欢迎登录学生管理系统-----------");
            System.out.println("1.添加学生信息");
            System.out.println("2.删除学生信息");
            System.out.println("3.修改学生信息");
            System.out.println("4.查询学生信息");
            System.out.println("5.退出");
            System.out.println("----------------------");

            //使用键盘录入供用户选择
            Scanner sc = new Scanner(System.in);
            int number = sc.nextInt();
            switch (number) {
                case 1:
                    addStudent(list);
                    break;
                case 2:
                    deleteStudent(list);
                    break;
                case 3:
                    updateStudent(list);
                    break;
                case 4:
                    queryStudent(list);
                    break;
                case 5: {
                    System.out.println("退出");
                    break loop;
                    //system.exit(0)---停止虚拟机运行
                }
                default:
                    System.out.println("没有这个选项");

            }
        }
    }

    //构造1托4结构，把增删改查四个功能的方法写出来

    public static void addStudent(ArrayList<Student> list) {
        Scanner sc = new Scanner(System.in);
        String id=null;
        loop:while (true) {
            System.out.println("请输入学生id");
            id = sc.next();
            boolean flag = contain(list, id);
            if (flag) {
                //true表示id已存在，需要重新录入id
                System.out.println("当前id已存在，需要重新录入");
            } else {
                break loop;
            }
        }

        System.out.println("请输入学生姓名");
        String name = sc.next();

        System.out.println("请输入学生年龄");
        int age = sc.nextInt();

        System.out.println("请输入学生家庭住址");
        String address = sc.next();

        Student s = new Student(name, age, id, address);
        list.add(s);

        System.out.println("信息添加成功");


    }

    public static void deleteStudent(ArrayList<Student> list) {
        Scanner sc=new Scanner(System.in);
        String sid=sc.next();
        //需要判断输入的id在集合中是否存在，创建一个方法
        if(getindex(list,sid)>=0){
            list.remove(getindex(list,sid));
            System.out.println("学生"+sid+"信息已删除成功");
        }else{
            System.out.println("系统暂不存在该学生信息");
        }
    }

    public static void updateStudent(ArrayList<Student> list) {
        System.out.println("请输入学生的id");
        Scanner sc=new Scanner(System.in);
        String sid=sc.next();
        int index =getindex(list,sid);
        if(index==-1){
            System.out.println("输入的id不存在，请重新输入");
            return;
        }
        //当代码执行到这里，表示id是存在的
        Student stu=list.get(index);
        System.out.println("请输入学生姓名");
        String newName=sc.next();
        stu.setName(newName);

        System.out.println("请输入学生年龄");
        int newAge=sc.nextInt();
        stu.setAge(newAge);

        System.out.println("请输入学生家庭住址");
        String newAddress=sc.next();
        stu.setAddress(newAddress);

        System.out.println("信息修改成功");
    }

    public static void queryStudent(ArrayList<Student> list) {
        System.out.println("查询学生信息");
        if (list.size() == 0) {
            System.out.println("当前无学生信息，请添加后查询");
            return;
        }
        //当代码执行到这里，表示集合有信息
        //打印表头信息
        System.out.println("id\t姓名\t年龄\t家庭住址");
        for (int i = 0; i < list.size(); i++) {
            Student stu = list.get(i);
            System.out.println(stu.getId() + "\t" + stu.getName() + "\t" + stu.getAge() + "\t" + stu.getAddress());
        }
    }

    //新建一个方法，判断id在集合中是否存在
    public static boolean contain(ArrayList<Student> list, String id) {
        for (int i = 0; i < list.size(); i++) {
            Student stu = list.get(i);
            String sid = stu.getId();
            if (sid.equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static int getindex(ArrayList<Student> list, String id) {
        for (int i = 0; i < list.size(); i++) {
            Student stu = list.get(i);
            String sid = stu.getId();
            if (sid.equals(id)) {
                return i;
            }
        }
        return -1;
    }
}

class Student {
    private String name;
    private int age;
    private String id;
    private String address;

    public Student() {
    }

    public Student(String name, int age, String id, String address) {
        this.name = name;
        this.age = age;
        this.id = id;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}





@Data
@AllArgsConstructor
class User {
    private String userName;
    private String password;
    private String idNumber;
    private String phoneNumber;

    public static void main(String[] args) {

    }

}
