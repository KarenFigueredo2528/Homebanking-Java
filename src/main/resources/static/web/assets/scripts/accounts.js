const { createApp } = Vue;

const options = {
  data() {
    return {
      clients: [],
      accounts: [],
      loans: [],
      numberAccount: ""
    };
  },
  created() {
    this.loadData();
  },
  methods: {
    loadData() {
      axios
        .get("http://localhost:8080/api/clients/current", { headers: { 'accept': 'application/json' } })
        .then((answer) => {
          this.clients = answer.data;
          console.log(answer);
          this.loans = this.clients.loans;
          this.accounts = this.clients.accounts.sort((a, b) => a.id - b.id).filter(account => account.accountStatus)
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
    createAccount() {
      Swal.fire({
        title: 'Do you want to create a new account?',
        inputAttributes: {
          autocapitalize: 'off'
        },
        showCancelButton: true,
        confirmButtonText: 'Yes',
        showLoaderOnConfirm: true,
        buttonColor: '#3085d6',
        preConfirm: login => {
          return axios.post("http://localhost:8080/api/clients/current/accounts")
            .then(answer => {
              location.reload()
              location.href = "./accounts.html"
            }).catch(error => {
              Swal.fire({
                icon: 'error',
                text: error.response.data,
                confirmButtonColor: '#3085d6'

              });
            })
        },
        alloweOutside: () => !Swal.isLoading()
      })
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