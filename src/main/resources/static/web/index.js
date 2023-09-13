const { createApp } = Vue;

const options = {
  data() {
    return {
      email: "",
      password: "",
      firstName: "",
      lastName: "",
    };
  },
  created() { },
  methods: {
    login() {
      axios
        .post("/api/login", `email=${this.email}&password=${this.password}`)
        .then((answer) => {
          if (this.email == "admin@admin.com") {
            location.href = "./admiPages/manager.html";
          } else {
            window.location.href = "./assets/pages/accounts.html";
          }
        })
        .catch((error) => {
          window.alert("Ups! Something is wrong with de data, try again");
        });
    },
    register(event) {
      event.preventDefault();
      console.log("si funciona... por lo menos entra");
      axios.post("/api/clients",`firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`,
          { headers: { "content-type": "application/x-www-form-urlencoded" } }
        )
        .then((response) => {
          this.login();
        })
        .catch((error) =>
          Swal.fire({
            title: "Error!",
            text: error.response.data,
            icon: "error",
            confirmButtonText: "Cool",
          })
        );
    },
  },
};

const app = createApp(options);
app.mount("#app");
