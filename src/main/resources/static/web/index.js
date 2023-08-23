const { createApp } = Vue;

const options = {
  data() {
    return {
      email: "",
      password: "",
    };
  },
  created() {},
  methods: {
    login() {
      axios
        .post("/api/login", `email=${this.email}&password=${this.password}`)
        .then((answer) => {
          window.location.href =
            "http://localhost:8080/web/assets/pages/accounts.html";
        })
        .catch((error) => {
            window.alert("The entered data is not registered, try again or create an account with us")
        });
    },
    register() {},
  },
};

const app = createApp(options);
app.mount("#app");
