console.log("Hola");
const { createApp } = Vue;

const options = {
  data() {
    return {
      name: "",
      clients: [],
      firstName: "",
      lastName: "",
      email: "",
      json: "",
    };
  },
  created() {
    this.loadData();
  },
  methods: {
    loadData() {
      axios
        .get("http://localhost:8080/api/clients")
        .then((answer) => {
          this.clients = answer.data;
          this.json = JSON.stringify(answer.data, null, 1);
        })
        .catch((error) => console.log(error));
    },
    addClient() {
      let newClient = {
        firstName: this.firstName,
        lastName: this.lastName,
        email: this.email,
      };
      axios
        .post("http://localhost:8080/api/clients", newClient)
        .then((answer) => {
          (this.firstName = ""),
            (this.lastName = ""),
            (this.email = ""),
            this.loadData();
        })
        .catch((error) => console.log(error));
    },
    voidInput() {
      if (this.firstName && this.lastName && this.email) {
        this.addClient();
      } else {
        alert("Please fill in all required fields");
      }
    },
    logOut() {
      axios
        .post("/api/logout")
        .then((response) => {
          location.href = "../index.html";
        })
        .catch((error) => console.log(error.message));
    },
  },
};

const app = createApp(options);
app.mount("#app");


/*Dark mode */
const switchButton = document.querySelector("#bg-dark");
const body = document.querySelector("body");
const form = document.querySelector("form");
const button = document.querySelector(".button-add");
switchButton.addEventListener("click", e => {
  body.classList.toggle("dark-mode"); 
  form.classList.toggle("dark-form");
  button.classList.toggle("dark-button");
});

