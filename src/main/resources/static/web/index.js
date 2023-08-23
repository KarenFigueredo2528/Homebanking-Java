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
            window.alert("Ups! Something is wrong with de data, try again")
        });
    },
    register() {},
  },
};

const app = createApp(options);
app.mount("#app");
