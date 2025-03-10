package hello.hello_spring.controller;

public class MemberForm {
    private String name;

    public String getName() {
        return name;
    }

    // name이 private으로 선언되었으므로, setter 메소드로 값을 변경함
    public void setName(String name) {
        this.name = name;
    }
}
