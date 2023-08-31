const { createApp } = Vue;

const options = {
  data() {
    return {
      clients: [],
      accounts: [],
      loans: [],
    };
  },
  created() {
    this.loadData();
  },
  methods: {
    loadData() {
      axios
        .get("http://localhost:8080/api/clients/current")
        .then((answer) => {
          this.clients = answer.data;
          console.log(this.client);
          this.loans = this.clients.loans;
          this.accounts = this.clients.accounts.sort((a, b) => a.id - b.id);
          console.log(this.loans);
        })
        .catch((error) => console.log(error));
    },
    logOut() {
      axios
        .post("/api/logout")
        .then((response) => {
          location.href = "../../index.html";
        })
        .catch((error) => console.log(error.message));
    },
    alert() {
      let mensaje;
      let opcion = confirm("Do you want to create a new account?");
      if (opcion == true) {
        axios
          .post("http://localhost:8080/api/clients/current/accounts")
          .then((response) => {
            location.href = "./accounts.html";
          })
          .catch((error) => {
            window.alert("You have reached the account limit");
          });
      }
      // } else {
      //     mensaje = "Cancel";
      // }
      // document.getElementById("ejemplo").innerHTML = mensaje;
    },
  },
};
const app = createApp(options);
app.mount("#app");
