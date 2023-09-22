const { createApp } = Vue;

const options = {
  data() {
    return {
      clients: [],
      accounts: [],
      loans: [],
      numberAccount: "",
      type:""
    };
  },
  created() {
    this.loadData();
  },
  methods: {
    loadData() {
      axios
        .get("/api/clients/current", { headers: { 'accept': 'application/json' } })
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
          console.log(this.type);
          return axios.post('/api/clients/current/accounts' , `typeAccount=${this.type}`)
            .then(answer => {
              location.reload()
              location.href = "./accounts.html"
            }).catch(error => {
              console.log(error.response);
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