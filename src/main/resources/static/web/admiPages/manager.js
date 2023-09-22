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
      typeLoan:"",
      maxAmount:0,
      payments:[],
      percentage:0
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
      Swal.fire({
        title: "Do you want to add a new client?",
        inputAttributes:{
            autocapitalize: 'off'
        },
        showCancelButton: true,
        confirmButtonText: 'Add client',
        showLoaderOnConfirm: true,
        buttonColor: '#3085d6',
      
        preConfirm: login =>{
            return  axios
            .post("http://localhost:8080/api/clients", newClient)
            .then((answer) => {
                (this.firstName = ""),
                    (this.lastName = ""),
                    (this.email = ""),
                    this.loadData();
            })
            .catch((error) => {
                Swal.fire({
                    icon: 'error',
                    text:error.response.data,
                    confirmButtonColor: '#3085d6'
                });
            });
        }
      }) 
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
    typeLoanCreate(){
      const createTypeLoan = {
        name: this.typeLoan,
        maxAmount : this.maxAmount,
        payments: this.payments,
        percentage: this.percentage
      }
      console.log(createTypeLoan);
      axios.post("/api/loans/create", createTypeLoan)
      .then(answer => {
        location.reload()
      }).catch(error => {
        console.log(error);
      })

    }
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


