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
        .get("http://localhost:8080/api/clients/current",{headers:{'accept':'application/xml'}})
        .then((answer) => {
          this.clients = answer.data;
          this.loans = this.clients.loans;
          this.accounts = this.clients.accounts.sort((a, b) => a.id - b.id);
          console.log(this.accounts);
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
    },
  },
};
const app = createApp(options);
app.mount("#app");

/*Dark mode */
const switchButton = document.querySelector("#bg-dark");
const body = document.querySelector("body");
switchButton.addEventListener("click", e => {
  body.classList.toggle("dark-mode"); 
});